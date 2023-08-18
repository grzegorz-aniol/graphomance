package org.gangel.graphomance.vendor.arangodb

import org.gangel.graphomance.api.Connection
import org.gangel.graphomance.api.Session
import org.gangel.graphomance.api.SessionProducer
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