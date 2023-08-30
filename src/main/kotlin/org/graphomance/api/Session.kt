package org.graphomance.api

import java.io.Closeable

interface Session : AutoCloseable, Closeable {
    override fun close()
    fun schemaApi(): SchemaApi
    fun objectApi(): ObjectApi
    fun runQuery(query: String, parameters: Map<String, Any> = emptyMap()): Result
    fun getDbType(): DbType
}