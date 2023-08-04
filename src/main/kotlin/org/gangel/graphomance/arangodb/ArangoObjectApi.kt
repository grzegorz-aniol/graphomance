package org.gangel.graphomance.arangodb

import com.arangodb.ArangoCollection
import com.arangodb.ArangoDatabase
import com.arangodb.entity.BaseDocument
import com.arangodb.entity.BaseEdgeDocument
import com.arangodb.util.MapBuilder
import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.ObjectApi
import org.gangel.graphomance.RelationIdentifier
import java.util.Optional

class ArangoObjectApi(
	val db: ArangoDatabase
) : ObjectApi {

	private fun classExists(clsName: String): Boolean {
		return db.collection(clsName)
			.exists()
	}

	override fun startTransaction() {}
	override fun commit() {}
	override fun rollback() {}
	override fun getNode(clsName: String, nodeId: NodeIdentifier): Any? {
		return db.collection(clsName)
			.getDocument(ArangoIdentifier.getKey(nodeId), BaseDocument::class.java)
	}

	override fun shortestPath(start: NodeIdentifier,
		end: NodeIdentifier
	): Long {
		val edgeClsName = "RoadTo"
		val TEMPL = String.format("""RETURN LENGTH(
  FOR v IN ANY
    SHORTEST_PATH @a TO @b %s
    RETURN v
)""", edgeClsName)
		val bindVars = MapBuilder()
			.put("a", ArangoIdentifier.Companion.getId(start))
			.put("b", ArangoIdentifier.Companion.getId(end))
			.get()
		val cursor = db.query(TEMPL, bindVars, null, Long::class.java)
		return if (cursor.hasNext()) {
			cursor.next()
		} else -1
	}

	override fun createNode(clsName: String,
		properties: Map<String, Any>
	): NodeIdentifier {
		val doc = BaseDocument()
		if (properties != null) {
			doc.properties = properties
		}
		val response = db.collection(clsName)
			.insertDocument(doc)
		return ArangoIdentifier(id = response.id, key = response.key)
	}

	override fun updateNode(clsName: String,
		nodeId: NodeIdentifier,
		properties: Map<String, Any>
	) {
		val doc = BaseDocument()
		if (properties != null) {
			doc.properties = properties
		}
		db.collection(clsName)
			.updateDocument(ArangoIdentifier.Companion.getKey(nodeId), doc)
	}

	override fun createRelation(className: String,
		fromNode: NodeIdentifier,
		toNode: NodeIdentifier,
		properties: Map<String, Any>
	): RelationIdentifier {
		val doc = BaseEdgeDocument()
		doc.from = ArangoIdentifier.getId(fromNode)
		doc.to = ArangoIdentifier.getId(toNode)
		if (properties != null) {
			doc.properties = properties
		}
		val docInserted = db.collection(className)
					 .insertDocument(doc)
		return ArangoIdentifier(
			key = docInserted.key,
			id = docInserted.id)
	}

	override fun updateRelation(clsName: String,
		edgeId: RelationIdentifier,
		properties: Map<String, Any>
	) {
		val doc = BaseDocument()
		if (properties != null) {
			doc.properties = properties
		}
		db.collection(clsName)
			.updateDocument(ArangoIdentifier.Companion.getKey(edgeId), doc)
	}

	override fun deleteAllNodes(clsName: String) {
		if (classExists(clsName)) {
			Optional.ofNullable(db.collection(clsName))
				.ifPresent { c: ArangoCollection -> c.drop() }
		}
	}

	override fun deleteAllRelations(clsName: String) {
		if (classExists(clsName)) {
			Optional.ofNullable(db.collection(clsName))
				.ifPresent { c: ArangoCollection -> c.drop() }
		}
	}
}