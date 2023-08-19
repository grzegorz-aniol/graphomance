package org.graphomance.metrics

import com.codahale.metrics.Gauge
import java.util.concurrent.TimeUnit
import org.apache.commons.lang3.time.StopWatch

class TimeGauge : Gauge<Double> {
	private var timer = StopWatch.createStarted()
	private var unit = TimeUnit.SECONDS

	constructor() {}
	constructor(unit: TimeUnit) {
		this.unit = unit
	}

	override fun getValue(): Double {
		return convert(timer.getTime(TimeUnit.MICROSECONDS))
	}

	fun convert(d: Long): Double {
		val m = unit.toMicros(1)
			.toDouble()
		return d / m
	}

	fun start() {
		timer = StopWatch.createStarted()
	}

	fun stop() {
		timer.stop()
	}
}