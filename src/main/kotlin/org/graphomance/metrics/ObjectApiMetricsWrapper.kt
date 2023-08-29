package org.graphomance.metrics

import org.graphomance.api.NodeIdentifier
import org.graphomance.api.ObjectApi
import org.graphomance.api.RelationshipIdentifier

class ObjectApiMetricsWrapper(private val delegate: ObjectApi) : ObjectApi {
    override fun startTransaction() {
        delegate.startTransaction()
    }

    override fun commit() {
        delegate.commit()
    }

    override fun rollback() {
        delegate.rollback()
    }

    override fun getNode(className: String, nodeId: NodeIdentifier): Any? {
        return Metrics.measure<Any?>(
            Metrics.GET_NODE
        ) {
            delegate.getNode(
                className,
                nodeId
            )
        }
    }

    override fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long {
        return Metrics.measure<Long>(
            Metrics.FIND_SHORTEST_PATH
        ) {
            delegate.shortestPath(
                start,
                end
            )
        }
    }

    override fun createNode(className: String, properties: Map<String, Any>): NodeIdentifier {
        return Metrics.measure<NodeIdentifier>(
            Metrics.CREATE_NODE
        ) {
            delegate.createNode(
                className,
                properties
            )
        }
    }

    override fun updateNode(className: String, nodeId: NodeIdentifier, properties: Map<String, Any>) {
        Metrics.measure(Metrics.UPDATE_NODE) { delegate.updateNode(className, nodeId, properties) }
    }

    override fun deleteNode(className: String, nodeId: NodeIdentifier) {
        Metrics.measure(Metrics.DELETE_NODE) { delegate.deleteNode(className, nodeId) }
    }

    override fun createRelationship(
        typeName: String,
        fromNode: NodeIdentifier,
        toNode: NodeIdentifier,
        properties: Map<String, Any>
    ): RelationshipIdentifier {
        return Metrics.measure<RelationshipIdentifier>(
            Metrics.CREATE_RELATION
        ) {
            delegate.createRelationship(
                typeName,
                fromNode,
                toNode,
                properties
            )
        }
    }

    override fun updateRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier, properties: Map<String, Any>) {
        Metrics.measure(
            Metrics.UPDATE_RELATION
        ) {
            delegate.updateRelationship(
                typeName,
                relationshipIdentifier,
                properties
            )
        }
    }

    override fun deleteRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier) {
        return Metrics.measure(Metrics.DELETE_RELATIONSHIP) {
            delegate.deleteRelationship(
                typeName,
                relationshipIdentifier
            )
        }
    }

    override fun deleteAllNodes(className: String) {
        delegate.deleteAllNodes(className)
    }

    override fun deleteAllRelationships(typeName: String) {
        delegate.deleteAllRelationships(typeName)
    }

    override fun cleanDatabase() {
        delegate.cleanDatabase()
    }

    companion object {
        fun create(delegate: ObjectApi): ObjectApiMetricsWrapper {
            return ObjectApiMetricsWrapper(delegate)
        }
    }

}