package org.gangel.graphomance.engine;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.SharedMetricRegistries;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.time.StopWatch;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.DbType;
import org.gangel.graphomance.SessionProducer;
import org.gangel.graphomance.metrics.Metrics;
import org.gangel.graphomance.neo4j.NeoConnectionProducer;
import org.gangel.graphomance.neo4j.NeoConnectionSettings;
import org.gangel.graphomance.orientdb.OrientConnectionProducer;
import org.gangel.graphomance.orientdb.OrientConnectionSettings;
import org.gangel.graphomance.orientdb.OrientSessionProducer;
import org.gangel.graphomance.usecases.*;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

        } else if (dbType == DbType.ORIENTDB){
            Objects.requireNonNull(dbName, "Database name for OrientDB is required");
            ConnectionProducer connProducer = new OrientConnectionProducer();
            OrientConnectionSettings connSetup = OrientConnectionSettings.builder()
                    .dbName(dbName)
//                .mode("remote")
                    .mode("plocal")
                    .dbPath(dbUrlOrPath)
                    .dbUser("admin").dbPassword("admin")
                    .dbAdminUser("root").dbAdminPassword("admin")
                    .build();
            connection = connProducer.connect(connSetup);
            sessionProducer = OrientSessionProducer.builder().opts(connSetup).build();
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
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MICROSECONDS)
            .build();
        //reporter.start(10, TimeUnit.SECONDS);

        List<TestBase> allTests = List.of(
            new CreateSingleVertex(),
            new CreateSingleVertexStringIndex(),
            new CreateSingleVertexStringUniqueIndex(),
            new CreateSingleVertexStringUniqueHashIndex(),
            new CreateSingleVertexLongIndex(),
            new CreateSingleVertexLongUniqueIndex(),
            new CreateSingleVertexLongUniqueHashIndex(),
            new CreateSingleVertexCompoundIndex(),
            new CreateSingleVertexCompoundUniqueIndex(),
            new CreateSingleVertexCompoundUniqueHashIndex()
        );

        System.out.printf("Starting with database: %s\n", dbType.toString());

        allTests.forEach((test)-> {
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

        reporter.report();
        csvReporter.report();
    }

    public static void runTest(TestBase test) {
        try {
            System.out.printf("Setup test: %s \n", test.getClass().getSimpleName());
            test.setUpTest();

            System.out.println("Starting test...");
            StopWatch timer = StopWatch.createStarted();
            test.performTest();
            timer.stop();
            System.out.println("Test done. Time: " + timer.toString());

            System.out.println("Cleaning up...");
            test.cleanUpAfter();
            System.out.println("Test done.");

        } catch (Throwable e) {
            System.out.println("Test error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
