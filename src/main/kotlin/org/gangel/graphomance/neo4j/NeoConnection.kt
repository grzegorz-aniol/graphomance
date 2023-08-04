package org.gangel.graphomance.neo4j

import org.gangel.graphomance.Connection
import org.neo4j.driver.Driver
import org.neo4j.driver.Session

class NeoConnection(private val driver: Driver) : Connection {
	override fun open() {}
	override fun close() {}

	fun session() : Session = driver.session()
}
