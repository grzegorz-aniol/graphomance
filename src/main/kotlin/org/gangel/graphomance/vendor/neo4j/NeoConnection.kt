package org.gangel.graphomance.vendor.neo4j

import org.gangel.graphomance.api.Connection
import org.neo4j.driver.Driver
import org.neo4j.driver.Session

class NeoConnection(private val driver: Driver) : Connection {
	override fun open() {}
	override fun close() {}

	fun session() : Session = driver.session()
}
