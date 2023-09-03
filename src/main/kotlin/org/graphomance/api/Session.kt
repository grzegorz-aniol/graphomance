package org.graphomance.api

import java.io.Closeable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

interface Session : AutoCloseable, Closeable {
    override fun close()
    fun schemaApi(): SchemaApi
    fun objectApi(): ObjectApi
    fun runQuery(query: String, parameters: Map<String, Any> = emptyMap()): Result
    fun getDbType(): DbType
    fun cleanDataInDatabase()
    fun convertDate(date: Date) =
        if (getDbType() == DbType.ARANGODB) {
            date
        } else {
            LocalDateTime.from(date.toInstant().atZone(ZoneOffset.UTC)).toLocalDate()
        }
}