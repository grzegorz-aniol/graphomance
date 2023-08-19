package org.graphomance.vendor.neo4j

import org.neo4j.driver.Driver
import org.neo4j.driver.Session

class NeoConnection(private val driver: Driver) : org.graphomance.api.Connection {
	override fun open() {}
	override fun close() {}

	fun session() : Session = driver.session()
}
