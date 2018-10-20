package org.gangel.graphomance.usecases;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.engine.TestLimit;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CreateSingleVertex extends TestBase {

    @Override
    public void setUpTest() {
        session.schemaApi().createClass("User");
        session.schemaApi().createProperty("User", "login", String.class, true);
        session.schemaApi().createProperty("User", "uid", String.class, true);
//        session.schemaApi().createIndex("User_uid", "User", IndexType.DEFAULT, true, "uid");
    }

    @Override
    public void createTestData() {

    }

    @Override
    public void performTest() {

        TestLimit limit = new TestLimit(10_000, Duration.ofSeconds(10));

        Timer timerMetric = new Timer();
        SharedMetricRegistries.getDefault().register("Create User vertex timer", timerMetric);

        Map<String, Object> props = new HashMap<>();

        long cnt = 0;
        while (!limit.isDone()) {
            props.put("login", "name_" + ++cnt);
            props.put("uid", "uid_" + cnt);
            try (final Timer.Context timer = timerMetric.time()){
                session.objectApi().createNode("User", props);
            }
            limit.increment();
        }
    }

    @Override
    public void cleanUpAfter() {
        session.objectApi().deleteAllNodes("User");
        session.schemaApi().dropClass("User");
    }
}
