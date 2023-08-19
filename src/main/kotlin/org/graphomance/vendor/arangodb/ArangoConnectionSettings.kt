package org.graphomance.vendor.arangodb

class ArangoConnectionSettings(
	val dbName: String = "test",
	val user: String,
	val password: String
) : org.graphomance.api.ConnectionSettings
