package org.gangel.graphomance.vendor.neo4j

import org.gangel.graphomance.api.ObjectApi
import org.gangel.graphomance.api.SchemaApi
import org.gangel.graphomance.api.Session
import org.gangel.graphomance.metrics.ObjectApiMetricsWrapper

internal class NeoSession(
		val connection: NeoConnection,
) : Session {

	val neo4jSession: org.neo4j.driver.Session = connection.session()
	private val schemaApi: SchemaApi
	private val objectApi: ObjectApi
	override fun close() {}
	override fun schemaApi() = schemaApi

	override fun objectApi() = objectApi

	init {
		schemaApi = NeoSchemaApi(this)
		objectApi = ObjectApiMetricsWrapper.create(NeoObjectApi(this))
	}
}