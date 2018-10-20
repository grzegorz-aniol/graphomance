package org.gangel.graphomance.engine;

import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.SessionProducer;
import org.gangel.graphomance.metrics.Metrics;
import org.gangel.graphomance.orientdb.OrientConnectionProducer;
import org.gangel.graphomance.orientdb.OrientConnectionSettings;
import org.gangel.graphomance.orientdb.OrientSessionProducer;
import org.gangel.graphomance.usecases.CreateSingleVertex;
import org.gangel.graphomance.usecases.TestBase;

public class TestLauncher {

    public static void main(String args[]) {

        Metrics.class.getSimpleName(); // load class;

        ConnectionProducer connProducer = new OrientConnectionProducer();
        OrientConnectionSettings connSetup = OrientConnectionSettings.builder()
                .dbName("perf")
//                .mode("remote")
//                .dbPath("localhost/DB")
                .mode("plocal")
                .dbPath("c:/dev/tools/orientdb-3.0.9/databases")
                .dbUser("admin").dbPassword("admin")
                .dbAdminUser("root").dbAdminPassword("admin")
                .build();
        Connection connection = connProducer.connect(connSetup);
        SessionProducer sessionProducer = OrientSessionProducer.builder().opts(connSetup).build();

        TestBase test = new CreateSingleVertex();
        test.initialize(connection, sessionProducer);

        System.out.println("Setup test...");
        test.setUpTest();
        System.out.println("Starting test...");
        test.performTest();
        System.out.println("Test done.");
        System.out.println("Cleaning up...");
        test.cleanUpAfter();
        System.out.println("Terminating...");
        test.terminate();
        System.out.println("Done.");
    }
}
