package org.graphomance.engine

import java.time.Duration

class TestLimit(private val maxIterations: Long,
	private val minDuration: Duration,
	private val maxDuration: Duration
) {
	private val startTime: Long = System.currentTimeMillis()
	private var cnt: Long = 0
	private val warmUpIterations: Long = 10
	fun increment() {
		++cnt
	}

	val isWarmUpPhase: Boolean
		get() = cnt < warmUpIterations

	val isDone: Boolean
		get() = if (System.currentTimeMillis() - startTime < minDuration.toMillis()) {
			false
		} else cnt + warmUpIterations >= maxIterations || System.currentTimeMillis() - startTime >= maxDuration.toMillis()

}