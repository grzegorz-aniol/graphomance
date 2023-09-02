package org.graphomance.vendor.arangodb

import com.arangodb.ArangoCollection
import com.arangodb.ArangoDatabase
import com.arangodb.entity.BaseDocument
import com.arangodb.entity.BaseEdgeDocument
import com.arangodb.util.MapBuilder
import java.util.Optional
import org.graphomance.api.NodeIdentifier
import org.graphomance.api.ObjectApi
import org.graphomance.api.RelationshipIdentifier

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

    override fun shortestPath(
        start: NodeIdentifier,
        end: NodeIdentifier
    ): Long {
        val edgeClsName = "RoadTo"
        val TEMPL = String.format(
            """RETURN LENGTH(
  FOR v IN ANY
    SHORTEST_PATH @a TO @b %s
    RETURN v
)""", edgeClsName
        )
        val bindVars = MapBuilder()
            .put("a", ArangoIdentifier.getId(start))
            .put("b", ArangoIdentifier.getId(end))
            .get()
        val cursor = db.query(TEMPL, bindVars, null, Long::class.java)
        return if (cursor.hasNext()) {
            cursor.next()
        } else -1
    }

    override fun createNode(
        clsName: String,
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

    override fun updateNode(
        clsName: String,
        nodeId: NodeIdentifier,
        properties: Map<String, Any>
    ) {
        val doc = BaseDocument()
        if (properties != null) {
            doc.properties = properties
        }
        db.collection(clsName)
            .updateDocument(ArangoIdentifier.getKey(nodeId), doc)
    }

    override fun deleteNode(className: String, nodeId: NodeIdentifier) {
        TODO("Not yet implemented")
    }

    override fun createRelationship(
        className: String,
        fromNode: NodeIdentifier,
        toNode: NodeIdentifier,
        properties: Map<String, Any>
    ): RelationshipIdentifier {
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
            id = docInserted.id
        )
    }

    override fun updateRelationship(
        clsName: String,
        edgeId: RelationshipIdentifier,
        properties: Map<String, Any>
    ) {
        val doc = BaseDocument()
        if (properties != null) {
            doc.properties = properties
        }
        db.collection(clsName)
            .updateDocument(ArangoIdentifier.getKey(edgeId), doc)
    }

    override fun deleteRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier) {
        TODO("Not yet implemented")
    }

    override fun deleteAllNodes(clsName: String) {
        if (classExists(clsName)) {
            Optional.ofNullable(db.collection(clsName))
                .ifPresent { c: ArangoCollection -> c.drop() }
        }
    }

    override fun deleteAllRelationships(clsName: String) {
        if (classExists(clsName)) {
            Optional.ofNullable(db.collection(clsName))
                .ifPresent { c: ArangoCollection -> c.drop() }
        }
    }

    override fun cleanDatabase() {
        db.collections.forEach { db.collection(it.name).drop() }
    }
}