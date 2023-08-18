package org.gangel.graphomance.vendor.neo4j

import org.gangel.graphomance.api.*
import org.gangel.graphomance.metrics.ObjectApiMetricsWrapper
import kotlin.streams.asSequence

internal class NeoSession(
    val connection: NeoConnection,
) : Session {

    val neo4jSession: org.neo4j.driver.Session = connection.session()
    private val schemaApi: SchemaApi
    private val objectApi: ObjectApi
    override fun close() {}
    override fun schemaApi() = schemaApi

    override fun objectApi() = objectApi
    override fun runQuery(query: String, parameters: Map<String, Any>): Result {
        return Result(
            rows = neo4jSession.run(query, parameters).stream().map { record ->
                    Row(values = Values(data = record.fields()
                        .associate { v -> v.key() to v.value().asObject() }))
                }.toList()
        )
    }

    init {
        schemaApi = NeoSchemaApi(this)
        objectApi = ObjectApiMetricsWrapper.create(NeoObjectApi(this))
    }
}