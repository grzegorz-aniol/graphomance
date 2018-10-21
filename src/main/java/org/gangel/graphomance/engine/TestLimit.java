package org.gangel.graphomance.engine;

import java.time.Duration;

public class TestLimit {
    private final long maxIterations;
    private final Duration minDuration;
    private final Duration maxDuration;
    private long startTime;
    private long cnt;
    private long warmUpIterations = 10;

    public TestLimit(long maxIterations, Duration minDuration, Duration maxDuration) {
        this.minDuration = minDuration;
        this.maxIterations = maxIterations;
        this.maxDuration = maxDuration;
        startTime = System.currentTimeMillis();
    }

    public void increment() {
        ++cnt;
    }

    public boolean isWarmUpPhase() {
        return (cnt < warmUpIterations);
    }

    public boolean isDone() {
        if (System.currentTimeMillis() - startTime < minDuration.toMillis()) {
            return false;
        }
        return (cnt + warmUpIterations >= maxIterations) || (System.currentTimeMillis() - startTime >= maxDuration.toMillis());
    }
}
