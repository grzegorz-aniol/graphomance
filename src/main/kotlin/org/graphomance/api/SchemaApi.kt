package org.graphomance.api

interface SchemaApi {
    fun countObjects(className: String): Long
    fun classExists(className: String): Boolean
    fun createClass(className: String)
    fun createRelationType(typeName: String)
    fun dropClass(className: String)
    fun createClasses(classNames: Iterable<String>) = classNames.forEach { createClass(it) }
    fun createRelationshipTypes(typeNames: Iterable<String>) = typeNames.forEach { createRelationType(it) }
}