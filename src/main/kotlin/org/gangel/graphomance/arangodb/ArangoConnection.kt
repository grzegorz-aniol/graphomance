package org.gangel.graphomance.arangodb

import com.arangodb.ArangoDB
import org.gangel.graphomance.Connection

class ArangoConnection(
	val db: ArangoDB,
	val connectionSettings: ArangoConnectionSettings
) : Connection {
	override fun open() {}
	override fun close() {
		db.shutdown()
	}
}
