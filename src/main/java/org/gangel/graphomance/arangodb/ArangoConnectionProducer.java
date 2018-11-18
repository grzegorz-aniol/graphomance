package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDB;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.ConnectionSettings;

import java.util.Objects;

public class ArangoConnectionProducer implements ConnectionProducer {

    @Override
    public Connection connect(ConnectionSettings settings) {
        ArangoConnectionSettings connSettings = Objects.requireNonNull((ArangoConnectionSettings) settings);
        ArangoDB arangoDB = new ArangoDB.Builder()
                .user(connSettings.getUser())
                .password(connSettings.getPassword())
                .build();
        return ArangoConnection.builder()
                .db(arangoDB)
                .connectionSettings(connSettings)
                .build();
    }

}
