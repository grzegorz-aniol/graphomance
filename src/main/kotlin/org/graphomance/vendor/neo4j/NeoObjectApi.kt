package org.graphomance.vendor.neo4j

import org.graphomance.api.NodeIdentifier
import org.graphomance.api.ObjectApi
import org.graphomance.api.RelationshipIdentifier
import org.neo4j.driver.QueryRunner
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

    override fun getNode(className: String, nodeId: NodeIdentifier): Any? {
        return null
    }

    override fun shortestPath(start: NodeIdentifier, end: NodeIdentifier): Long {
        return 0L
    }

    override fun createNode(className: String, properties: Map<String, Any>): NodeIdentifier {
        return getQueryRunner().run(
            """
                create (n:`$className` ${'$'}map) 
                return ID(n) as ident
            """.trimIndent(), mapOf("map" to properties)
        ).single().let {
            NeoIdentifier(it.get("ident").asLong())
        }
    }

    override fun updateNode(className: String, nodeId: NodeIdentifier, properties: Map<String, Any>) {
        getQueryRunner().run(
            """
                match (n:`$className`)
                where ID(n)=${'$'}ident
                set n += ${'$'}map
            """.trimIndent(), mapOf("ident" to NeoIdentifier.toLong(nodeId), "map" to properties)
        )
    }

    override fun deleteNode(className: String, nodeId: NodeIdentifier) {
        getQueryRunner().run(
            """
                 match (n:`$className`)
                 where ID(n) = ${'$'}id
                 detach delete n
            """.trimIndent(),
            mapOf("id" to NeoIdentifier.toLong(nodeId))
        )
    }

    override fun createRelationship(
        typeName: String,
        fromNode: NodeIdentifier,
        toNode: NodeIdentifier,
        properties: Map<String, Any>
    ): RelationshipIdentifier {
        val query = """
            match (a), (b)
            where ID(a)=${'$'}fromNode and ID(b)=${'$'}toNode
            create (a)-[r:`$typeName`]->(b) 
            return ID(r) as ident
        """.trimIndent()
        return getQueryRunner().run(
            query,
            mapOf(
                "map" to properties,
                "fromNode" to NeoIdentifier.toLong(fromNode),
                "toNode" to NeoIdentifier.toLong(toNode)
            )
        ).single().let {
            NeoIdentifier(it.get("ident").asLong())
        }
    }

    override fun updateRelationship(
        typeName: String,
        relationshipIdentifier: RelationshipIdentifier,
        properties: Map<String, Any>
    ) {
        val query = """
            match ()-[r:`$typeName`]-()
            where ID(r)=${'$'}ident
            set r += ${'$'}map
        """.trimIndent()
        getQueryRunner().run(query, mapOf("ident" to NeoIdentifier.toLong(relationshipIdentifier), "map" to properties))
    }

    override fun deleteRelationship(typeName: String, relationshipIdentifier: RelationshipIdentifier) {
        val query = """
            match ()-[r:`$typeName`]-()
            where ID(r)=${'$'}ident
            delete r
        """.trimIndent()
        getQueryRunner().run(query, mapOf("ident" to NeoIdentifier.toLong(relationshipIdentifier)))
    }

    override fun deleteAllNodes(className: String) {
        getQueryRunner().run("match (n:`$className`) delete n").consume()
    }

    override fun deleteAllRelationships(typeName: String) {
        getQueryRunner().run("match ()-[r:`$typeName`]->() delete r").consume()
    }

    override fun cleanDatabase() {
        getQueryRunner().run("match (n) detach delete n")
    }

    private fun getQueryRunner(): QueryRunner = tx ?: session.neo4jSession

}