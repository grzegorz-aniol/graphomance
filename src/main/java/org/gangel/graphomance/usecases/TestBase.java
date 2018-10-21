package org.gangel.graphomance.usecases;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.SharedMetricRegistries;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.SessionProducer;
import org.gangel.graphomance.TestCase;
import org.gangel.graphomance.metrics.Metrics;

import java.util.concurrent.TimeUnit;

public abstract class TestBase implements TestCase {

    protected Connection conn;

    protected SessionProducer sessionProducer;

    protected Session session;

    @Override
    public void initialize(Connection conn, SessionProducer sessionProducer) {
        this.conn = conn;
        this.sessionProducer = sessionProducer;
        this.session = sessionProducer.createSession(conn);
    }

    @Override
    public void terminate() {
        this.session.close();
    }

}
