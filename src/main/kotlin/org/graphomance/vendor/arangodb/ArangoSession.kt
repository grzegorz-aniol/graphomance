package org.graphomance.vendor.arangodb

import com.arangodb.ArangoDatabase
import org.graphomance.api.DbType
import org.graphomance.api.Result
import org.graphomance.api.SchemaApi
import org.graphomance.api.Session

class ArangoSession(
	val db: ArangoDatabase,
	val connectionSettings: ArangoConnectionSettings
) : Session {

	override fun close() {}
	override fun schemaApi(): SchemaApi {
		return ArangoSchemaApi(db = db)
	}

	override fun objectApi() = ArangoObjectApi(db = db)

	override fun runQuery(query: String, parameters: Map<String, Any>): Result {
		TODO("Not yet implemented")
	}

	override fun getDbType() = DbType.ARANGODB

}