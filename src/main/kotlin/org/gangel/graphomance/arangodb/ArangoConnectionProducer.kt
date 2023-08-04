package org.gangel.graphomance.arangodb

import com.arangodb.ArangoDB
import org.gangel.graphomance.Connection
import org.gangel.graphomance.ConnectionProducer
import org.gangel.graphomance.ConnectionSettings

class ArangoConnectionProducer : ConnectionProducer {
	override fun connect(settings: ConnectionSettings): Connection {
		val connSettings = settings as ArangoConnectionSettings ?: throw RuntimeException("Required ArangoDB")
		val arangoDB = ArangoDB.Builder()
			.user(connSettings.user)
			.password(connSettings.password)
			.build()
		return ArangoConnection(db = arangoDB, connectionSettings = connSettings)
	}
}