package org.gangel.graphomance.usecases;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CreateSingleVertex extends TestBase {

    @Override
    public void setUpTest() {
        final String query = "create class User if not exists extends V;\n" +
                "create property User.login if not exists STRING (mandatory, notnull);\n" +
                "create property User.uid if not exists STRING\t(mandatory, notnull);\n";
//                "create index IDX_USER_ID if not exists on User(uid) unique_hash_index;";
//                "create index IDX_USER_ID if not exists on User(uid) unique;";
        session.runScript(query);
    }

    @Override
    public void createTestData() {

    }

    private static class TestLimit {
        private long maxIterations = 1;
        private Duration minDuration = Duration.ofSeconds(1);
        private long startTime;
        private long cnt;

        public TestLimit(long minIter, Duration minDuration) {
            this.minDuration = minDuration;
            this.maxIterations = minIter;
            startTime = System.currentTimeMillis();
        }

        void increment() {
            ++cnt;
        }

        boolean isDone() {
            if (System.currentTimeMillis() - startTime < minDuration.toMillis()) {
                return false;
            }
            return (cnt >= maxIterations);
        }
    }

    @Override
    public void performTest() {

        TestLimit limit = new TestLimit(10_000, Duration.ofSeconds(30));

        Map<String, Object> props = new HashMap<>();

        long cnt = 0;
        while (!limit.isDone()) {
            props.put("login", "name_" + ++cnt);
            props.put("uid", "uid_" + cnt);
            session.createNode("User", props);
            limit.increment();
        }
    }

    @Override
    public void cleanUpAfter() {
        session.runScript("delete vertex User; drop class User;");
    }
}
