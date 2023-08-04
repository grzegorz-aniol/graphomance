package org.gangel.graphomance.arangodb

import com.arangodb.ArangoDatabase
import org.gangel.graphomance.ObjectApi
import org.gangel.graphomance.SchemaApi
import org.gangel.graphomance.Session
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