package org.gangel.graphomance.orientdb;

import lombok.Builder;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.SessionProducer;

import java.util.Objects;

@Builder
public class OrientSessionProducer implements SessionProducer {

    private OrientConnectionSettings opts;

    @Override
    public Session createSession(Connection connection) {
        OrientConnection c = Objects.requireNonNull((OrientConnection)connection);
        return OrientSession.builder().session(c.getDb().open(opts.getDbName(), opts.getDbUser(), opts.getDbPassword()))
                .build();
    }

}
