package org.graphomance.vendor.arangodb

import com.arangodb.ArangoDB

class ArangoConnection(
	val db: ArangoDB,
	val connectionSettings: ArangoConnectionSettings
) : org.graphomance.api.Connection {
	override fun open() {}
	override fun close() {
		db.shutdown()
	}
}
