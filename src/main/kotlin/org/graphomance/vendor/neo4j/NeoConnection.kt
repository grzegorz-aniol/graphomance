package org.graphomance.vendor.neo4j

import org.neo4j.driver.Driver
import org.neo4j.driver.Session
import org.neo4j.driver.SessionConfig

class NeoConnection(private val driver: Driver, private val dbName: String?) : org.graphomance.api.Connection {
	override fun open() {}
	override fun close() {}

	val config = dbName?.let { SessionConfig.forDatabase(it) } ?: SessionConfig.defaultConfig()
	fun session() : Session = driver.session(config)
}
