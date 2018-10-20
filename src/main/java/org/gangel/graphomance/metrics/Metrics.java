package org.gangel.graphomance.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import java.util.concurrent.atomic.AtomicReference;

public class Metrics {

    private Metrics() {
    }

    public static Counter CREATED_VERTICES_COUNT = new Counter();

    public static Meter CREATED_VERTICES_METER = new Meter();

    public static void init() {
        MetricRegistry registry = new MetricRegistry();
        registry.register("Num of vertices created", CREATED_VERTICES_COUNT);
        registry.register("Vertices time", CREATED_VERTICES_METER);
        SharedMetricRegistries.setDefault("default", registry);
    }
}
