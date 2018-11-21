package org.gangel.graphomance.metrics;

import com.codahale.metrics.Gauge;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class TimeGauge implements Gauge<Double> {

    private StopWatch timer = StopWatch.createStarted();

    public TimeGauge() {
    }

    @Override
    public Double getValue() {
        return Double.valueOf(timer.getTime(TimeUnit.MICROSECONDS));
    }

    public void start() {
        timer = StopWatch.createStarted();
    }

    public void stop() {
        timer.stop();
    }
}
