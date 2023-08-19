package org.graphomance.vendor.arangodb

import java.util.Objects
import org.graphomance.api.Session
import org.graphomance.api.SessionProducer

class ArangoSessionProducer : SessionProducer {
	override fun createSession(connection: org.graphomance.api.Connection): Session {
		val conn: ArangoConnection = Objects.requireNonNull(connection as ArangoConnection)
		val db = conn.db
			.db(conn.connectionSettings
					.dbName)
		return ArangoSession(db = db, connectionSettings = conn.connectionSettings)
	}
}