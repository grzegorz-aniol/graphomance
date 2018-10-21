package org.gangel.graphomance.engine;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.SharedMetricRegistries;
import org.apache.commons.lang3.time.StopWatch;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.SessionProducer;
import org.gangel.graphomance.metrics.Metrics;
import org.gangel.graphomance.orientdb.OrientConnectionProducer;
import org.gangel.graphomance.orientdb.OrientConnectionSettings;
import org.gangel.graphomance.orientdb.OrientSessionProducer;
import org.gangel.graphomance.usecases.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestLauncher {

    public static void main(String args[]) {

        Metrics.init();

        ConnectionProducer connProducer = new OrientConnectionProducer();
        OrientConnectionSettings connSetup = OrientConnectionSettings.builder()
                .dbName("perf")
//                .mode("remote")
//                .dbPath("localhost/DB")
                .mode("plocal")
                .dbPath("D:/dev/orientdb-3.0.9/databases")
                .dbUser("admin").dbPassword("admin")
                .dbAdminUser("root").dbAdminPassword("admin")
                .build();
        Connection connection = connProducer.connect(connSetup);
        SessionProducer sessionProducer = OrientSessionProducer.builder().opts(connSetup).build();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(SharedMetricRegistries.getDefault())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MICROSECONDS)
                .build();
        //reporter.start(10, TimeUnit.SECONDS);

        List<TestBase> allTests = List.of(
                new CreateSingleVertex(),
                new CreateSingleVertexLongIndex(),
                new CreateSingleVertexStringIndex(),
                new CreateSingleVertexLongUniqueIndex(),
                new CreateSingleVertexStringUniqueIndex(),
                new CreateSingleVertexCompoundIndex(),
                new CreateSingleVertexCompoundUniqueIndex(),
                new CreateSingleVertexCompoundUniqueHashIndex(),
                new CreateSingleVertexStringUniqueIndex(),
                new CreateSingleVertexLongUniqueIndex()
        );

        allTests.forEach((test)-> {
            try {
                test.initialize(connection, sessionProducer);
                runTest(test);
            } finally {
                test.terminate();
            }
        });

        reporter.report();
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
        }
    }
}
