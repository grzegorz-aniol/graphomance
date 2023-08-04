package org.gangel.graphomance.metrics

import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.ObjectApi
import org.gangel.graphomance.RelationIdentifier

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

	override fun getNode(clsName: String, nodeId: NodeIdentifier): Any? {
		return Metrics.measure<Any?>(
			Metrics.GET_NODE
		) {
			delegate.getNode(
				clsName,
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

	override fun createNode(clsName: String, properties: Map<String, Any>): NodeIdentifier {
		return Metrics.measure<NodeIdentifier>(
			Metrics.CREATE_NODE
		) {
			delegate.createNode(
				clsName,
				properties
			)
		}
	}

	override fun updateNode(clsName: String, nodeId: NodeIdentifier, properties: Map<String, Any>) {
		Metrics.measure(
			Metrics.UPDATE_NODE
		) { delegate.updateNode(clsName, nodeId, properties) }
	}

	override fun createRelation(className: String, fromNode: NodeIdentifier, toNode: NodeIdentifier, properties: Map<String, Any>): RelationIdentifier {
		return Metrics.measure<RelationIdentifier>(
			Metrics.CREATE_RELATION
		) {
			delegate.createRelation(
				className,
				fromNode,
				toNode,
				properties
			)
		}
	}

	override fun updateRelation(clsName: String, edgeId: RelationIdentifier, properties: Map<String, Any>) {
		Metrics.measure(
			Metrics.UPDATE_RELATION
		) {
			delegate.updateRelation(
				clsName,
				edgeId,
				properties
			)
		}
	}

	override fun deleteAllNodes(clsName: String) {
		delegate.deleteAllNodes(clsName)
	}

	override fun deleteAllRelations(clsName: String) {
		delegate.deleteAllRelations(clsName)
	}

	companion object {
		fun create(delegate: ObjectApi): ObjectApiMetricsWrapper {
			return ObjectApiMetricsWrapper(delegate)
		}
	}

}