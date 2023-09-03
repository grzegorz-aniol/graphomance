package org.graphomance.vendor.arangodb

import com.arangodb.ArangoCollection
import com.arangodb.ArangoDatabase
import com.arangodb.entity.BaseDocument
import com.arangodb.entity.BaseEdgeDocument
import com.arangodb.model.DocumentDeleteOptions
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
    override fun getNode(className: String, nodeId: NodeIdentifier): Any? {
        return db.collection(className)
            .getDocument(ArangoIdentifier.getKey(nodeId), BaseDocument::class.java)
    }

    override fun shortestPath(
        start: NodeIdentifier,
        end: NodeIdentifier
    ): Long = 0

    override fun createNode(
        className: String,
        properties: Map<String, Any>
    ): NodeIdentifier {
        val doc = BaseDocument()
        doc.properties = properties
        val response = db.collection(className)
            .insertDocument(doc)
        return ArangoIdentifier(id = response.id, key = response.key)
    }

    override fun updateNode(
        className: String,
        nodeId: NodeIdentifier,
        properties: Map<String, Any>
    ) {
        val doc = BaseDocument()
        doc.properties = properties
        db.collection(className)
            .updateDocument(ArangoIdentifier.getKey(nodeId), doc)
    }

    override fun deleteNode(className: String, nodeId: NodeIdentifier) {
        db.collection(className).deleteDocument(ArangoIdentifier.getKey(nodeId))
    }

    override fun createRelationship(
        typeName: String,
        fromNode: NodeIdentifier,
        toNode: NodeIdentifier,
        properties: Map<String, Any>
    ): RelationshipIdentifier {
        val doc = BaseEdgeDocument()
        doc.from = ArangoIdentifier.getId(fromNode)
        doc.to = ArangoIdentifier.getId(toNode)
        doc.properties = properties
        val docInserted = db.collection(typeName)
            .insertDocument(doc)
        return ArangoIdentifier(
            key = docInserted.key,
            id = docInserted.id
        )
    }

    override fun updateRelationship(
        typeName: String,
        relationshipIdentifier: RelationshipIdentifier,
        properties: Map<String, Any>
    ) {
        val doc = BaseDocument()
        doc.properties = properties
        db.collection(typeName)
            .updateDocument(ArangoIdentifier.getKey(relationshipIdentifier), doc)
    }

    override fun deleteRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier) {
        db.collection(typeName).deleteDocument(ArangoIdentifier.getKey(relationshipIdentifier))
    }

    override fun deleteAllNodes(className: String) {
        if (classExists(className)) {
            Optional.ofNullable(db.collection(className))
                .ifPresent { c: ArangoCollection -> c.drop() }
        }
    }

    override fun deleteAllRelationships(typeName: String) {
        if (classExists(typeName)) {
            Optional.ofNullable(db.collection(typeName))
                .ifPresent { c: ArangoCollection -> c.drop() }
        }
    }

    override fun cleanDatabase() {
        db.collections.forEach { db.collection(it.name).drop() }
    }
}