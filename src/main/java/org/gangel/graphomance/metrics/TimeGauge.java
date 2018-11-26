package org.gangel.graphomance.metrics;

import com.codahale.metrics.Gauge;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class TimeGauge implements Gauge<Double> {

    private StopWatch timer = StopWatch.createStarted();

    private TimeUnit unit = TimeUnit.SECONDS;

    public TimeGauge() {
    }

    public TimeGauge(TimeUnit unit) {
        this.unit = unit;
    }

    @Override
    public Double getValue() {
        return convert(timer.getTime(TimeUnit.MICROSECONDS));
    }

    public Double convert(long d) {
        double m = unit.toMicros(1);
        return d / m;
    }

    public void start() {
        timer = StopWatch.createStarted();
    }

    public void stop() {
        timer.stop();
    }
}
