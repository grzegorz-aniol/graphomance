package org.graphomance.usecases

import org.graphomance.api.Session
import org.graphomance.api.TestCase
import org.slf4j.Logger

abstract class TestBase : TestCase {

	protected fun stageMapBuilder(testName: String,
								  stage: (Session)->Unit,
								  otherStages: Map<String, (Session)->Unit> = emptyMap()
	): Map<String, (Session)->Unit> {
		val map = mutableMapOf<String, (Session)->Unit>()
		map[testName] = stage
		otherStages.forEach { (stageName, action) -> map[stageName] = action }
		return map
	}

	open val stages: Map<String, (Session)->Unit>
		get() = mapOf(this.javaClass.simpleName to ::performTest)

	override fun skipFor(): Array<org.graphomance.api.DbType>? {
		return null
	}

	protected fun logResult(log: Logger, result: org.graphomance.api.Result) {
		result.rows.forEach { row ->
			log.info(row.values.values.joinToString(separator = ",") { it.toString() })
		}
	}
}