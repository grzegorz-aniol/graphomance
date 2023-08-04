package org.gangel.graphomance.arangodb

import org.gangel.graphomance.Connection
import org.gangel.graphomance.Session
import org.gangel.graphomance.SessionProducer
import java.util.Objects

class ArangoSessionProducer : SessionProducer {
	override fun createSession(connection: Connection): Session {
		val conn: ArangoConnection = Objects.requireNonNull(connection as ArangoConnection)
		val db = conn.db
			.db(conn.connectionSettings
					.dbName)
		return ArangoSession(db = db, connectionSettings = conn.connectionSettings)
	}
}