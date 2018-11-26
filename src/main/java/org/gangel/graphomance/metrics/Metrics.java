package org.gangel.graphomance.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;

import java.util.function.Supplier;

public class Metrics {

    private Metrics() {
    }

    public static Timer GET_NODE = new Timer();
    public static Timer GET_RELATION = new Timer();
    public static Timer CREATE_NODE = new Timer();
    public static Timer CREATE_RELATION = new Timer();
    public static Timer UPDATE_NODE = new Timer();
    public static Timer UPDATE_RELATION = new Timer();

    public static Timer FIND_SHORTEST_PATH = new Timer();

    public static void init() {
        MetricRegistry registry = new MetricRegistry();
        registry.register("CreateNode", CREATE_NODE);
        registry.register("CreateRelation", CREATE_RELATION);
        registry.register("GetNode", GET_NODE);
        registry.register("GetRelation", GET_RELATION);
        registry.register("UpdateNode", UPDATE_NODE);
        registry.register("UpdateRelation", UPDATE_RELATION);
        registry.register("FindShortestPath", FIND_SHORTEST_PATH);
        SharedMetricRegistries.setDefault("default", registry);
    }

    public static <T> T measure(Timer metric, Supplier<T> body) {
        try(Timer.Context ts = metric.time()) {
            return body.get();
        }
    }

    public static void measure(Timer metric, Runnable body) {
        try(Timer.Context ts = metric.time()) {
            body.run();
        }
    }

}
