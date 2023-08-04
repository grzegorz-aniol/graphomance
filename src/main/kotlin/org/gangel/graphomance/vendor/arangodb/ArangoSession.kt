package org.gangel.graphomance.vendor.arangodb

import com.arangodb.ArangoDatabase
import org.gangel.graphomance.api.ObjectApi
import org.gangel.graphomance.api.SchemaApi
import org.gangel.graphomance.api.Session
import org.gangel.graphomance.metrics.ObjectApiMetricsWrapper

class ArangoSession(
	val db: ArangoDatabase,
	val connectionSettings: ArangoConnectionSettings
) : Session {

	override fun close() {}
	override fun schemaApi(): SchemaApi {
		return ArangoSchemaApi(db = db)
	}

	override fun objectApi(): ObjectApi {
		return ObjectApiMetricsWrapper.create(ArangoObjectApi(db = db))
	}

}