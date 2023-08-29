package org.graphomance.api

interface ObjectApi {
    fun startTransaction()
    fun commit()
    fun rollback()
    fun getNode(className: String, nodeId: NodeIdentifier): Any?
    fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long

    fun createNode(className: String, properties: Map<String, Any> = emptyMap()): NodeIdentifier

    fun updateNode(className: String, nodeId: NodeIdentifier, properties: Map<String, Any>)

    fun deleteNode(className: String, nodeId: NodeIdentifier)

    fun createRelationship(
        typeName: String,
        fromNode: NodeIdentifier,
        toNode: NodeIdentifier,
        properties: Map<String, Any> = emptyMap()
    ): RelationshipIdentifier

    fun updateRelationship(
        typeName: String,
        relationshipIdentifier: RelationshipIdentifier,
        properties: Map<String, Any>
    )

    fun deleteRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier)

    fun deleteAllNodes(className: String)
    fun deleteAllRelationships(typeName: String)
    fun cleanDatabase()
}