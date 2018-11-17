package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDB;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.Connection;

@Builder
public class ArangoConnection implements Connection {

    @Getter
    private ArangoDB db;

    @Getter
    private ArangoConnectionSettings connectionSettings;

    @Override
    public void open() {
    }

    @Override
    public void close() {
        db.shutdown();
    }
}
