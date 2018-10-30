package org.gangel.graphomance.usecases;

import org.gangel.graphomance.*;

public abstract class TestBase implements TestCase {

    protected Connection conn;

    protected SessionProducer sessionProducer;

    protected Session session;

    protected ObjectApi objectApi;
    protected SchemaApi schemaApi;


    @Override
    public void initialize(Connection conn, SessionProducer sessionProducer) {
        this.conn = conn;
        this.sessionProducer = sessionProducer;
        this.session = sessionProducer.createSession(conn);
        objectApi = session.objectApi();
        schemaApi = session.schemaApi();
    }

    @Override
    public void terminate() {
        this.session.close();
    }

    @Override
    public DbType[] skipFor() {
        return null;
    }
}
