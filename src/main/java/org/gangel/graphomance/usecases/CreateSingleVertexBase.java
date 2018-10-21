package org.gangel.graphomance.usecases;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.engine.TestLimit;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class CreateSingleVertexBase extends TestBase {

    protected final String timerName;
    protected final IndexType indexType;
    protected final boolean isIndexLong;
    protected final boolean isIndexString;
    final static String USER_CLASS = "UserNode";


    protected CreateSingleVertexBase(String timerName, IndexType indexType, boolean indexLong, boolean indexString) {
        this.timerName = timerName;
        this.indexType = indexType;
        this.isIndexLong = indexLong;
        this.isIndexString = indexString;
    }

    @Override
    public void setUpTest() {
        if (session.schemaApi().classExists(USER_CLASS)) {
            session.objectApi().deleteAllNodes(USER_CLASS);
        }

        session.schemaApi().createClass(USER_CLASS);
        session.schemaApi().createProperty(USER_CLASS, "login", String.class, true);
        session.schemaApi().createProperty(USER_CLASS, "uid", Long.class, true);
        if (isIndexString && isIndexLong) {
            if (indexType != null) {
                session.schemaApi().createIndex("User_uid_login", USER_CLASS, indexType, true, "uid, login");

            }
        } else {
            if (isIndexString && indexType != null) {
                session.schemaApi().createIndex("User_login", USER_CLASS, indexType, true, "login");
            }
            if (isIndexLong && indexType != null) {
                session.schemaApi().createIndex("User_uid", USER_CLASS, indexType, true, "uid");
            }
        }
    }

    @Override
    public void createTestData() {
    }

    @Override
    public void performTest() {
        TestLimit limit = new TestLimit(1_000, Duration.ofSeconds(5), Duration.ofSeconds(30) );

        Timer timerMetric = new Timer();
        SharedMetricRegistries.getDefault().register(timerName, timerMetric);

        Map<String, Object> props = new HashMap<>();

        long cnt = 0;
        while (!limit.isDone()) {
            ++cnt;
            props.put("login", "name_" + cnt);
            props.put("uid", cnt);
            try (final Timer.Context timer = timerMetric.time()){
                session.objectApi().createNode(USER_CLASS, props);
            }
            limit.increment();
        }

        System.out.printf("End of operations. # of iterations: %d\n", cnt);
    }

    @Override
    public void cleanUpAfter() {
        session.objectApi().deleteAllNodes(USER_CLASS);
        session.schemaApi().dropClass(USER_CLASS);
    }

}
