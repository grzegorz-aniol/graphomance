package org.gangel.graphomance.api

class Values(private val data: Map<String, Any?> = emptyMap()) : Map<String, Any?> by data {
    fun asString(col: String): String? = data[col] as String?
    fun asInt(col: String): Int? = data[col] as Int?
}

data class Row(val values: Values)

data class Result(val rows: Iterable<Row>)
