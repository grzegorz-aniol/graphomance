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

    protected ConsoleReporter reporter;

    @Override
    public void initialize(Connection conn, SessionProducer sessionProducer) {
        Metrics.init();
        this.conn = conn;
        this.sessionProducer = sessionProducer;
        this.session = sessionProducer.createSession(conn);

        reporter = ConsoleReporter.forRegistry(SharedMetricRegistries.getDefault())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(10, TimeUnit.SECONDS);
    }

    @Override
    public void terminate() {
        reporter.report();

        this.session.close();
        this.conn.close();
    }

}
