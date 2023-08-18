package org.gangel.graphomance.vendor.arangodb

import com.arangodb.ArangoDB
import org.gangel.graphomance.api.Connection

class ArangoConnection(
	val db: ArangoDB,
	val connectionSettings: ArangoConnectionSettings
) : Connection {
	override fun open() {}
	override fun close() {
		db.shutdown()
	}
}
