package org.gangel.graphomance.api

interface ObjectApi {
    fun startTransaction()
    fun commit()
    fun rollback()
    fun getNode(clsName: String, nodeId: NodeIdentifier): Any?
    fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long

    fun createNode(clsName: String): NodeIdentifier {
        return createNode(clsName)
    }

    fun createNode(clsName: String, properties: Map<String, Any> = emptyMap()): NodeIdentifier

    fun updateNode(clsName: String, nodeId: NodeIdentifier, properties: Map<String, Any>)

    fun createRelation(className: String, fromNode: NodeIdentifier, toNode: NodeIdentifier): RelationIdentifier {
        return createRelation(className, fromNode, toNode)
    }

    fun createRelation(className: String, fromNode: NodeIdentifier, toNode: NodeIdentifier, properties: Map<String, Any> = emptyMap()): RelationIdentifier

    fun updateRelation(clsName: String, edgeId: RelationIdentifier, properties: Map<String, Any>)

    fun deleteAllNodes(clsName: String)
    fun deleteAllRelations(clsName: String)
}