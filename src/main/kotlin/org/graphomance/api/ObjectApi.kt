package org.graphomance.api

interface ObjectApi {
    fun startTransaction()
    fun commit()
    fun rollback()
    fun getNode(className: String, nodeId: NodeIdentifier): Any?
    fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long

    fun createNode(className: String, properties: Map<String, Any> = emptyMap()): NodeIdentifier

    fun updateNode(className: String, nodeId: NodeIdentifier, properties: Map<String, Any>)

    fun createRelation(typeName: String, fromNode: NodeIdentifier, toNode: NodeIdentifier, properties: Map<String, Any> = emptyMap()): RelationIdentifier

    fun updateRelation(typeName: String, edgeId: RelationIdentifier, properties: Map<String, Any>)

    fun deleteAllNodes(className: String)
    fun deleteAllRelations(typeName: String)
    fun cleanDatabase()
}