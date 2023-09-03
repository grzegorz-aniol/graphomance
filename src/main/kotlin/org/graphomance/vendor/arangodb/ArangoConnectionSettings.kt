package org.graphomance.vendor.arangodb

class ArangoConnectionSettings(
	val host: String = "localhost",
	val port: Int = 8529,
	val dbName: String = "test",
	val user: String,
	val password: String
) : org.graphomance.api.ConnectionSettings
