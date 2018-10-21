package org.gangel.graphomance.neo4j;

import org.gangel.graphomance.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.Objects;

public class NeoConnectionProducer implements ConnectionProducer, SessionProducer {

    @Override
    public Connection connect(ConnectionSettings settings) {
        NeoConnectionSettings opts = Objects.requireNonNull((NeoConnectionSettings) settings, "Requiring Neo4j connection settings");

        GraphDatabaseService dbService = new GraphDatabaseFactory().newEmbeddedDatabase(new File(opts.getDbPath()));
        registerShutdownHook( dbService );

        return NeoConnection.builder().dbService(dbService).build();
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread( () -> graphDb.shutdown() ));
    }

    @Override
    public Session createSession(Connection connection) {
        return new NeoSession(Objects.requireNonNull((NeoConnection) connection, "Requiring Neo4j connection object"));
    }
}
