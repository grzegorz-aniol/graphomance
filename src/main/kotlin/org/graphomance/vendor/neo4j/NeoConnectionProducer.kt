package org.graphomance.vendor.neo4j

import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.api.SessionProducer
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase

class NeoConnectionProducer(private val dbType: DbType = DbType.NEO4J) : org.graphomance.api.ConnectionProducer,
    SessionProducer {
    override fun connect(settings: org.graphomance.api.ConnectionSettings): org.graphomance.api.Connection {
        if (settings !is NeoConnectionSettings)
            throw RuntimeException("Requiring Neo4j connection settings")
        val opts: NeoConnectionSettings = settings
        return NeoConnection(
            driver = GraphDatabase.driver(opts.dbPath, AuthTokens.basic("neo4j", "password")),
            dbName = opts.dbName
        )
    }

    override fun createSession(connection: org.graphomance.api.Connection): Session {
        val neoConnection = connection as? NeoConnection ?: throw RuntimeException("Requiring Neo4j connection object")
        return NeoSession(neoConnection, dbType)
    }

}