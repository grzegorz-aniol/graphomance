package org.gangel.graphomance.vendor.arangodb

import com.arangodb.ArangoDB
import org.gangel.graphomance.api.Connection
import org.gangel.graphomance.api.ConnectionProducer
import org.gangel.graphomance.api.ConnectionSettings

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