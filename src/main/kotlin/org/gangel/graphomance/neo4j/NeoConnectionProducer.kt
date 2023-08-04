package org.gangel.graphomance.neo4j

import org.gangel.graphomance.*
import org.neo4j.driver.AuthToken
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase

class NeoConnectionProducer : ConnectionProducer, SessionProducer {
	override fun connect(settings: ConnectionSettings): Connection {
		if (settings !is NeoConnectionSettings)
			throw RuntimeException("Requiring Neo4j connection settings")
		val opts: NeoConnectionSettings = settings
		return NeoConnection(GraphDatabase.driver(opts.dbPath, AuthTokens.basic("neo4j", "password")))
	}

	override fun createSession(connection: Connection): Session {
		val neoConnection = connection as? NeoConnection ?: throw RuntimeException("Requiring Neo4j connection object")
		return NeoSession(neoConnection)
	}

}