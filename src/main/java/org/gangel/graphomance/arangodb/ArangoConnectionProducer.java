package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDB;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.ConnectionSettings;

public class ArangoConnectionProducer implements ConnectionProducer {

    @Override
    public Connection connect(ConnectionSettings settings) {
        ArangoDB arangoDB = new ArangoDB.Builder().build();
        return ArangoConnection.builder().db(arangoDB).build();
    }

}
