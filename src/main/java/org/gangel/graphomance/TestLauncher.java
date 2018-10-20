package org.gangel.graphomance;

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
                    .dbName("DB")
                .mode("remote")
                .dbPath("localhost/DB")
//                .dbPath("d:/dev/orientdb-3.0.8/databases")
                .dbUser("admin").dbPassword("admin")
                .dbAdminUser("root").dbAdminPassword("admin")
                .build();
        Connection connection = connProducer.connect(connSetup);
        SessionProducer sessionProducer = OrientSessionProducer.builder().opts(connSetup).build();

        TestBase test = new CreateSingleVertex();
        test.initialize(connection, sessionProducer);

        test.setUpTest();
        test.performTest();
        test.cleanUpAfter();
        test.terminate();
    }
}
