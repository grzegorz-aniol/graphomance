package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.SessionProducer;

import java.util.Objects;

public class ArangoSessionProducer implements SessionProducer {
    @Override
    public Session createSession(Connection connection) {
        ArangoConnection conn = Objects.requireNonNull((ArangoConnection) connection);
        ArangoDatabase db = conn.getDb().db("name");
        return ArangoSession.builder().db(db).build();
    }
}
