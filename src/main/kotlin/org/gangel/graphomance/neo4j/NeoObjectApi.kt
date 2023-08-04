package org.gangel.graphomance.neo4j

import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.ObjectApi
import org.gangel.graphomance.RelationIdentifier
import org.neo4j.driver.Transaction

class NeoObjectApi internal constructor(private val session: NeoSession) : ObjectApi {
    private var tx: Transaction? = null
    override fun startTransaction() {
        if (tx != null) {
            throw RuntimeException("Transaction is already active")
        }
        tx = session.neo4jSession.beginTransaction()
    }

    override fun commit() {
        tx?.commit() ?: throw RuntimeException("Transaction is not active")
        tx = null
    }

    override fun rollback() {
        tx?.rollback() ?: throw RuntimeException("Transaction is not active")
        tx = null
    }

    override fun getNode(clsName: String, nodeId: NodeIdentifier): Any? {
        return null
    }

    override fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long {
        return 0L
    }

    override fun createNode(className: String, properties: Map<String, Any>): NodeIdentifier {
        return session.neo4jSession.run("""
            create (n:`$className` ${'$'}map) 
            return ID(n) as ident
        """.trimIndent(), mapOf("map" to properties)).single().let {
            NeoIdentifier(it.get("ident").asLong())
        }
    }

    override fun updateNode(clsName: String, nodeId: NodeIdentifier, properties: Map<String, Any>) {
        throw RuntimeException("Not implemented!")
    }

    override fun createRelation(typeName: String, fromNode: NodeIdentifier, toNode: NodeIdentifier, properties: Map<String, Any>): RelationIdentifier {
        return session.neo4jSession.run("""
            match (a), (b)
            where ID(a)=${'$'}fromNode and ID(b)=${'$'}toNode
            create (a)-[r:`$typeName`]->(b) RETURN ID(r) 
            return ID(r) as ident
        """.trimIndent(), mapOf("map" to properties)).single().let {
            NeoIdentifier(it.get("ident").asLong())
        }
    }

    override fun updateRelation(clsName: String, edgeId: RelationIdentifier, properties: Map<String, Any>) {
        throw RuntimeException("Not implemented!")
    }

    override fun deleteAllNodes(className: String) {
        session.neo4jSession.run("match (n:`$className`) delete n").consume()
    }

    override fun deleteAllRelations(typeName: String) {
        session.neo4jSession.run("match ()-[r:`$typeName`]->() delete r").consume()
    }

}