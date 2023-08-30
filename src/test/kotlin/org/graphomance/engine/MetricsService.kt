package org.graphomance.engine

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.CsvReporter
import com.codahale.metrics.MetricAttribute
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.SharedMetricRegistries
import com.codahale.metrics.Timer
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

object MetricsService {
    init {
        val registry = MetricRegistry()
        SharedMetricRegistries.setDefault("default", registry)
    }

    fun registerTimeGaugeMetric(testName: String, metricsName: String): Timer {
        val t = Timer()
        SharedMetricRegistries.getDefault()
            .register(String.format("%s.%s", testName, metricsName), t)
        return t
    }

    fun reportMetrics() {
        val csvReporter = CsvReporter.forRegistry(SharedMetricRegistries.getDefault())
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .formatFor(Locale.US)
            .build(File("./build/"))
        val reporter = ConsoleReporter.forRegistry(SharedMetricRegistries.getDefault())
            .formattedFor(Locale.US)
            .disabledMetricAttributes(
                setOf(
                    MetricAttribute.P75,
                    MetricAttribute.P98,
                    MetricAttribute.P999,
                    MetricAttribute.M1_RATE,
                    MetricAttribute.M5_RATE,
                    MetricAttribute.M15_RATE
                )
            )
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
        reporter.report()
        csvReporter.report()

        // remove all existing metrics
        SharedMetricRegistries.getDefault().removeMatching { _, _ -> true }
    }

}