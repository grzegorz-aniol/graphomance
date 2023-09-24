package org.graphomance.api

import java.io.Closeable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import org.graphomance.engine.DbByTypeQueries
import org.graphomance.engine.getQuery

interface Session : AutoCloseable, Closeable {
    override fun close()

    fun schemaApi(): SchemaApi

    fun objectApi(): ObjectApi

    fun runQuery(query: String, parameters: Map<String, Any> = emptyMap()): Result

    fun runQuery(queries: DbByTypeQueries, parameters: Map<String, Any> = emptyMap()): Result {
        return queries.getQuery(getDbType())
            .takeIf { it.isNotBlank() }
            ?.let {
                runQuery(it, parameters)
            }
            ?: Result(emptyList())
    }

    fun getDbType(): DbType

    fun cleanDataInDatabase()

    fun convertDate(date: Date) =
        if (getDbType() == DbType.ARANGODB) {
            date
        } else {
            LocalDateTime.from(date.toInstant().atZone(ZoneOffset.UTC)).toLocalDate()
        }
}