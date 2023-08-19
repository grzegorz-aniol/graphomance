package org.graphomance.api

interface SchemaApi {
    fun countObjects(className: String): Long
    fun classExists(className: String): Boolean
    fun createClass(className: String)
    fun createRelationClass(className: String)
    fun dropClass(className: String)
}