package org.gangel.graphomance.engine;

import com.codahale.metrics.*;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.time.StopWatch;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.DbType;
import org.gangel.graphomance.SessionProducer;
import org.gangel.graphomance.arangodb.ArangoConnectionProducer;
import org.gangel.graphomance.arangodb.ArangoConnectionSettings;
import org.gangel.graphomance.arangodb.ArangoSessionProducer;
import org.gangel.graphomance.metrics.Metrics;
import org.gangel.graphomance.metrics.TimeGauge;
import org.gangel.graphomance.neo4j.NeoConnectionProducer;
import org.gangel.graphomance.neo4j.NeoConnectionSettings;
import org.gangel.graphomance.orientdb.OrientConnectionProducer;
import org.gangel.graphomance.orientdb.OrientConnectionSettings;
import org.gangel.graphomance.orientdb.OrientSessionProducer;
import org.gangel.graphomance.usecases.CreateBasicRelationTest;
import org.gangel.graphomance.usecases.TestBase;
import org.gangel.graphomance.usecases.node.CreateSingleVertex;
import org.gangel.graphomance.usecases.relation.CreateRelationsInFlatStructure;
import org.gangel.graphomance.usecases.relation.CreateRelationsInStarStructure;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricAttribute.*;

public class TestLauncher {

  final static String BREAKE_LINE = "\n-------------------------------------------------------------------\n";

  public static void main(String args[]) {

    Options opts = new Options();
    opts.addOption(Option.builder("t").longOpt("type").required().hasArg().build());
    opts.addOption(Option.builder("u").longOpt("dburl").required().hasArg().build());
    opts.addOption(Option.builder("n").longOpt("dbname").required(false).hasArg().build());
    CommandLineParser parser = new DefaultParser();
    CommandLine line;
    try {
      line = parser.parse(opts, args);
    } catch (ParseException e) {
      System.err.println(e.getMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("Graphomance", opts);
      return;
    }

    Metrics.init();

    Connection connection;
    SessionProducer sessionProducer;

    String dbUrlOrPath = line.getOptionValue("u");
    String dbName = line.getOptionValue("n");
    String dbKind = line.getOptionValue("t");

    DbType dbType = DbType.of(dbKind);
    if (dbType == null) {
      System.err.println("Unknown database type");
      return;
    }

    if (dbType == DbType.NEO4J) {
      NeoConnectionProducer connProducer = new NeoConnectionProducer();
      NeoConnectionSettings connSetup = NeoConnectionSettings.builder()
          .dbPath(dbUrlOrPath)
          .build();
      connection = connProducer.connect(connSetup);
      sessionProducer = connProducer;

    } else if (dbType == DbType.ORIENTDB) {
      Objects.requireNonNull(dbName, "Database name for OrientDB is required");
      ConnectionProducer connProducer = new OrientConnectionProducer();
      OrientConnectionSettings connSetup = OrientConnectionSettings.builder()
          .dbName(dbName)
          .dbPath(dbUrlOrPath)
          .dbUser("admin").dbPassword("admin")
          .dbAdminUser("root").dbAdminPassword("admin")
          .build();
      connection = connProducer.connect(connSetup);
      sessionProducer = OrientSessionProducer.builder().opts(connSetup).build();

    } else if (dbType == DbType.ARANGODB) {
      Objects.requireNonNull(dbName, "Database name for ArangoDB is required!");
      ConnectionProducer connProducer = new ArangoConnectionProducer();
      ArangoConnectionSettings connSettings = ArangoConnectionSettings.builder()
          .dbName(dbName)
          .user("root")
          .password("admin")
          .build();
      connection = connProducer.connect(connSettings);
      sessionProducer = ArangoSessionProducer.builder().build();

    } else {
      System.err.println("Unsupported database type");
      return;
    }

    CsvReporter csvReporter = CsvReporter.forRegistry(SharedMetricRegistries.getDefault())
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MICROSECONDS)
        .formatFor(Locale.US)
        .build(new File("."));

    ConsoleReporter reporter = ConsoleReporter.forRegistry(SharedMetricRegistries.getDefault())
        .formattedFor(Locale.US)
        .disabledMetricAttributes(Set.of(STDDEV, P50, P75, P95, P98, P999, M1_RATE, M5_RATE, M15_RATE))
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MICROSECONDS)
        .build();

    List<TestBase> allTests = List.of(
        new CreateBasicRelationTest(),
        new CreateSingleVertex(),
        new CreateRelationsInFlatStructure(),
        new CreateRelationsInStarStructure()
//            new CreateSingleVertexStringIndex(),
//            new CreateSingleVertexStringUniqueIndex(),
//            new CreateSingleVertexStringUniqueHashIndex(),
//            new CreateSingleVertexLongIndex(),
//            new CreateSingleVertexLongUniqueIndex(),
//            new CreateSingleVertexLongUniqueHashIndex(),
//            new CreateSingleVertexCompoundIndex(),
//            new CreateSingleVertexCompoundUniqueIndex(),
//            new CreateSingleVertexCompoundUniqueHashIndex(),
    );

    System.out.printf("Starting with database: %s\n", dbType.toString());

    allTests.forEach((test) -> {
      System.out.printf(BREAKE_LINE);
      if (test.skipFor(dbType)) {
        System.out.printf("Skipping test '%s'\n", test.getClass().getSimpleName());
      } else {
        try {
          test.initialize(connection, sessionProducer);
          runTest(test);
        } finally {
          test.terminate();
        }
      }
    });

    connection.close();

    reporter.report();
    csvReporter.report();
  }

  private static void addNewMetric(String testName, String metricsName, Runnable body) {
    TimeGauge t = new TimeGauge();
    SharedMetricRegistries.getDefault().register(String.format("%s.%s", testName, metricsName), t);

    t.start();
    body.run();
    t.stop();
  }

  public static void runTest(TestBase test) {

    try {
      final String testName = test.getClass().getSimpleName();
      System.out.printf("Setup test: %s \n", testName);
      test.setUpTest();

      System.out.println("Cleaning up...");
      test.cleanUpData();

      System.out.printf("Test data generation...\n");
      addNewMetric(testName, "1.DataGeneration", () -> test.createTestData());


      StopWatch timer = StopWatch.createStarted();

      LinkedHashMap<String, Runnable> stages = test.getStages();
      int stageNum = 0;
      Iterator<Map.Entry<String, Runnable>> iterStages = stages.entrySet().iterator();
      while (iterStages.hasNext()) {
        ++stageNum;
        Map.Entry<String, Runnable> stageInfo = iterStages.next();

        System.out.printf(" > running test stage %d - %s..\n", stageNum, stageInfo.getKey());
        addNewMetric(testName, stageInfo.getKey(), stageInfo.getValue());
      }


      timer.stop();
      System.out.println("Test done. Time: " + timer.toString());

      System.out.println("Cleaning up...");
      addNewMetric(testName, "3.CleanUp", () -> test.cleanUpData());

      System.out.println("Test done.");

    } catch (Throwable e) {
      System.out.println("Test error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
