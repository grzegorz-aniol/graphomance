package org.gangel.graphomance.arangodb

import org.gangel.graphomance.ConnectionSettings

class ArangoConnectionSettings(
	val dbName: String = "test",
	val user: String,
	val password: String
) : ConnectionSettings
