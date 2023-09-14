package org.graphomance.usecases.fraud

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.TestTimer
import org.jetbrains.letsPlot.core.spec.getInt
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FirstPartyFraud : FraudTestBase() {

    // PII - personally identifiable information

    @BeforeAll
    fun `generate SHARED_IDENTIFIERS relationships`(session: Session) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]->(n)<-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]-(c2:Client)
                    WHERE elementId(c1) < elementId(c2)
                    WITH c1, c2, count(*) as cnt
                    MERGE (c1)-[:SHARED_IDENTIFIERS {count: cnt}]->(c2)
                    RETURN count(*) as count
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]->(n)<-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]-(c2:Client)
                    WHERE ID(c1) < ID(c2)
                    WITH c1, c2, count(*) as cnt
                    MERGE (c1)-[:SHARED_IDENTIFIERS {count: cnt}]->(c2)
                    RETURN count(*) as count
                """.trimIndent()

            else -> throw RuntimeException("Unsupported database")
        }
        assertThat(session.runQuery(query).rows.firstOrNull()?.values?.getInt("count")).isGreaterThan(0)

        if (session.getDbType() == DbType.NEO4J) {
            val projectionQuery = """
                    CALL {
                      CALL gds.graph.drop('wcc', false) YIELD graphName as _
                    }
                    CALL gds.graph.project('wcc',
                        {
                            Client: {
                                label: 'Client'
                            }
                        },
                        {
                            SHARED_IDENTIFIERS:{
                                type: 'SHARED_IDENTIFIERS',
                                orientation: 'UNDIRECTED',
                                properties: {
                                    count: {
                                        property: 'count'
                                    }
                                }
                            }
                        }
                    ) YIELD graphName,nodeCount,relationshipCount,projectMillis
                    RETURN graphName
            """.trimIndent()
            session.runQuery(projectionQuery)
        }
    }

    @Test
    @Order(1)
    fun `Identify clients sharing PII`(session: Session, testTimer: TestTimer) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]->(n)<-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]-(c2:Client)
                    WHERE elementId(c1) < elementId(c2)
                    RETURN c1.id, c2.id, count(*) AS freq
                    ORDER BY freq DESC;                    
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]->(n)<-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]-(c2:Client)
                    WHERE ID(c1) < ID(c2)
                    RETURN c1.id, c2.id, count(*) AS freq
                    ORDER BY freq DESC;                    
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database")
        }
        repeat(100) {
            val result = testTimer.timeMeasureWithResult {
                session.runQuery(query).rows.toList()
            }
            assertThat(result).isNotEmpty()
        }
    }

    @Test
    @Order(2)
    fun `Identify groups of clients sharing PII - fraud rings`(session: Session, testTimer: TestTimer) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                CALL gds.wcc.stream('wcc',
                    {
                        nodeLabels: ['Client'],
                        relationshipTypes: ['SHARED_IDENTIFIERS'],
                        consecutiveIds: true
                    }
                )
                YIELD componentId, nodeId
                WITH componentId AS cluster, gds.util.asNode(nodeId) AS client
                WITH cluster, collect(client.id) AS clients
                WITH cluster, clients, size(clients) AS clusterSize WHERE clusterSize > 1
                UNWIND clients AS client
                MATCH (c:Client) WHERE c.id = client
                SET c.firstPartyFraudGroup=cluster
                RETURN count(*)
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                MATCH p=(:Client)-[:SHARED_IDENTIFIERS]-()
                WITH project(p) as clients_graph
                CALL weakly_connected_components.get(clients_graph)
                YIELD node, component_id
                WITH component_id as cluster, collect(ID(node)) AS clients
                WITH cluster, clients, size(clients) AS clusterSize WHERE clusterSize > 1
                UNWIND clients AS client
                MATCH (c:Client) WHERE c.id = client
                SET c.firstPartyFraudGroup=cluster
                RETURN count(*)                    
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database")
        }
        repeat(100) {
            val result = testTimer.timeMeasureWithResult {
                session.runQuery(query).rows.toList()
            }
            assertThat(result).isNotEmpty()
        }
    }

    @AfterAll
    fun `remove created data`(session: Session) {
        session.runQuery("""
            MATCH ()-[r:SHARED_IDENTIFIERS]->()
            DELETE r
        """.trimIndent())
    }

}