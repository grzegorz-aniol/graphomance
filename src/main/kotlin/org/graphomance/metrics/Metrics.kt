package org.graphomance.metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.SharedMetricRegistries
import com.codahale.metrics.Timer
import java.util.function.Supplier

object Metrics {
    var GET_NODE = Timer()
    var GET_RELATION = Timer()
    var CREATE_NODE = Timer()
    var CREATE_RELATION = Timer()
    var UPDATE_NODE = Timer()
    var UPDATE_RELATION = Timer()
    var DELETE_NODE = Timer()
    val DELETE_RELATIONSHIP = Timer()
    var FIND_SHORTEST_PATH = Timer()
    fun init() {
        val registry = MetricRegistry()
        registry.register("CreateNode", CREATE_NODE)
        registry.register(
            "CreateRelation",
            CREATE_RELATION
        )
        registry.register("GetNode", GET_NODE)
        registry.register(
            "GetRelation",
            GET_RELATION
        )
        registry.register("UpdateNode", UPDATE_NODE)
        registry.register(
            "UpdateRelation",
            UPDATE_RELATION
        )
        registry.register(
            "FindShortestPath",
            FIND_SHORTEST_PATH
        )
        registry.register("DeleteNode", DELETE_NODE)
        registry.register("DeleteRelationship", DELETE_RELATIONSHIP)
        SharedMetricRegistries.setDefault("default", registry)
    }

    fun <T> measure(metric: Timer?, body: Supplier<T>): T {
        metric!!.time()
            .use { ts -> return body.get() }
    }

    fun measure(metric: Timer?, body: Runnable) {
        metric!!.time()
            .use { ts -> body.run() }
    }
}