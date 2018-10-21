package org.gangel.graphomance.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;

import java.util.concurrent.atomic.AtomicReference;

public class Metrics {

    private Metrics() {
    }

    public static Meter CREATED_VERTICES_METER = new Meter();
    public static Meter CREATED_RELATION_METER = new Meter();

    public static void init() {
        MetricRegistry registry = new MetricRegistry();
        registry.register("Creating node", CREATED_VERTICES_METER);
        registry.register("Creating relation", CREATED_RELATION_METER);
        SharedMetricRegistries.setDefault("default", registry);
    }
}
