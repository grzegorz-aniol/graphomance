package org.graphomance.vendor.arangodb

import com.arangodb.ArangoDB

class ArangoConnectionProducer : org.graphomance.api.ConnectionProducer {
	override fun connect(settings: org.graphomance.api.ConnectionSettings): org.graphomance.api.Connection {
		val connSettings = settings as ArangoConnectionSettings ?: throw RuntimeException("Required ArangoDB")
		val arangoDB = ArangoDB.Builder()
			.user(connSettings.user)
			.password(connSettings.password)
			.build()
		return ArangoConnection(db = arangoDB, connectionSettings = connSettings)
	}
}