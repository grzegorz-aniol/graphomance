package org.gangel.graphomance.neo4j;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.Connection;
import org.neo4j.graphdb.GraphDatabaseService;

@Builder
public class NeoConnection implements Connection {

    @Getter
    private GraphDatabaseService dbService;

    @Override
    public void open() {

    }

    @Override
    public void close() {
        if (dbService != null) {
            dbService.shutdown();
            dbService = null;
        }
    }
}
