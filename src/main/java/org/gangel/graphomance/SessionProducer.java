package org.gangel.graphomance;

public interface SessionProducer {

    Session createSession(Connection connection);
}
