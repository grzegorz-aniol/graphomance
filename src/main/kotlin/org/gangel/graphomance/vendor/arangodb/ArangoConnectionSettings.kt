package org.gangel.graphomance.vendor.arangodb

import org.gangel.graphomance.api.ConnectionSettings

class ArangoConnectionSettings(
	val dbName: String = "test",
	val user: String,
	val password: String
) : ConnectionSettings
