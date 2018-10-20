package org.gangel.graphomance.engine;

import java.time.Duration;

public class TestLimit {
    private long maxIterations = 1;
    private Duration minDuration = Duration.ofSeconds(1);
    private long startTime;
    private long cnt;

    public TestLimit(long minIter, Duration minDuration) {
        this.minDuration = minDuration;
        this.maxIterations = minIter;
        startTime = System.currentTimeMillis();
    }

    public void increment() {
        ++cnt;
    }

    public boolean isDone() {
        if (System.currentTimeMillis() - startTime < minDuration.toMillis()) {
            return false;
        }
        return (cnt >= maxIterations);
    }
}
