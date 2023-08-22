package org.graphomance.vendor.neo4j

import org.graphomance.api.DbType
import org.graphomance.api.ObjectApi
import org.graphomance.api.Result
import org.graphomance.api.Row
import org.graphomance.api.SchemaApi
import org.graphomance.api.Session
import org.graphomance.api.Values
import org.graphomance.metrics.ObjectApiMetricsWrapper

internal class NeoSession(
    val connection: NeoConnection,
    private val dbType: DbType
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

    override fun getDbType() = dbType

    init {
        schemaApi = NeoSchemaApi(this)
        objectApi = ObjectApiMetricsWrapper.create(NeoObjectApi(this))
    }
}