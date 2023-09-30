package org.graphomance.usecases.fraud

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.QueryTimer
import org.graphomance.engine.dbByTypeQueries
import org.jetbrains.letsPlot.core.spec.getInt
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
class FirstPartyFraud : FraudTestBase() {

    // PII - personally identifiable information

    @BeforeAll
    fun `generate SHARED_IDENTIFIERS relationships`(session: Session) {
        val deleteSharedIdentifiers = dbByTypeQueries(
            DbType.NEO4J to """MATCH ()-[r:SHARED_IDENTIFIERS]->() DELETE r""",
            DbType.MEMGRAPH to """MATCH ()-[r:SHARED_IDENTIFIERS]->() DELETE r""",
        )
        session.runQuery(deleteSharedIdentifiers)

        val generateRelationshipsQuery = dbByTypeQueries(
            DbType.NEO4J to
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]->(n)<-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]-(c2:Client)
                    WHERE elementId(c1) < elementId(c2)
                    WITH c1, c2, count(*) as cnt
                    MERGE (c1)-[:SHARED_IDENTIFIERS {count: cnt}]->(c2)
                    RETURN count(*) as count
                """.trimIndent(),
            DbType.MEMGRAPH to
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]->(n)<-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]-(c2:Client)
                    WHERE ID(c1) < ID(c2)
                    WITH c1, c2, count(*) as cnt
                    MERGE (c1)-[:SHARED_IDENTIFIERS {count: cnt}]->(c2)
                    RETURN count(*) as count
                """.trimIndent()
        )
        assertThat(session.runQuery(generateRelationshipsQuery).rows.firstOrNull()?.values?.getInt("count")).isEqualTo(759)

        // Create 'wcc' projection
        if (session.getDbType() == DbType.NEO4J) {
            val projectionQuery = """
                    CALL {  CALL gds.graph.drop('wcc', false) YIELD graphName as _ }
                    CALL gds.graph.project('wcc',
                        { Client: { label: 'Client' } },
                        { SHARED_IDENTIFIERS: 
                            { 
                                type: 'SHARED_IDENTIFIERS', orientation: 'UNDIRECTED',                        
                                properties: { count: { property: 'count' } } 
                            } 
                        }
                    ) YIELD graphName,nodeCount,relationshipCount,projectMillis
                    RETURN graphName
            """.trimIndent()
            assertThat(session.runQuery(projectionQuery).rows).isNotEmpty()
        }
    }

    @Test
    @Order(1)
    fun `Identify clients sharing PII`(session: Session, testTimer: QueryTimer) {
        val query = dbByTypeQueries(
            DbType.NEO4J to
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]->(n)<-[:HAS_EMAIL|HAS_PHONE|HAS_SSN]-(c2:Client)
                    WHERE elementId(c1) < elementId(c2)
                    RETURN c1.id, c2.id, count(*) AS freq
                    ORDER BY freq DESC                    
                """.trimIndent(),
            DbType.MEMGRAPH to
                """
                    MATCH (c1:Client)-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]->(n)<-[:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]-(c2:Client)
                    WHERE ID(c1) < ID(c2)
                    RETURN c1.id, c2.id, count(*) AS freq
                    ORDER BY freq DESC                    
                """.trimIndent()
        )
        repeat(100) {
            val result = testTimer.timeMeasureWithResult {
                session.runQuery(query).rows.toList()
            }
            assertThat(result).isNotEmpty()
        }
    }

    @Test
    @Order(2)
    fun `Identify groups of clients sharing PII - fraud rings`(session: Session, testTimer: QueryTimer) {
        val query = dbByTypeQueries(
            DbType.NEO4J to
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
                RETURN count(*) as count
                """.trimIndent(),
            DbType.MEMGRAPH to
                """
                MATCH p=(:Client)-[:SHARED_IDENTIFIERS]-()
                WITH project(p) as clients_graph
                CALL weakly_connected_components.get(clients_graph)
                YIELD node as client, component_id
                WITH component_id as cluster, collect(client.id) AS clients
                WITH cluster, clients, size(clients) AS clusterSize WHERE clusterSize > 1
                UNWIND clients AS client
                MATCH (c:Client) WHERE c.id = client
                SET c.firstPartyFraudGroup=cluster
                RETURN count(*) as count           
                """.trimIndent()
        )
        repeat(100) {
            val result = testTimer.timeMeasureWithResult {
                session.runQuery(query).rows.toList()
            }
            assertThat(result.first().values.asLong("count")).isEqualTo(336L)
        }
    }

    /**
     * Expected score distribution for bidirectional SIMILAR_TO results
     * ╒═══════╤════════╕
     * │r.score│count(*)│
     * ╞═══════╪════════╡
     * │0.2    │966     │
     * ├───────┼────────┤
     * │0.5    │474     │
     * ├───────┼────────┤
     * │1.0    │78      │
     * └───────┴────────┘
     */
    @Test
    @Order(3)
    fun `pairwise JACARD similarity scores`(session: Session, testTimer: QueryTimer) {
        val writeSimilarityQuery = dbByTypeQueries(
            DbType.NEO4J to
            """
                CALL { CALL gds.graph.drop('similarity', false) YIELD graphName as _ }                
                MATCH (c:Client) WHERE c.firstPartyFraudGroup IS NOT NULL
                OPTIONAL MATCH (c)-[r:HAS_EMAIL|HAS_PHONE|HAS_SSN]->(id)
                WITH gds.graph.project('similarity', c, id, {
                    sourceNodeLabels: labels(c),
                    targetNodeLabels: labels(id),
                    relationshipType: 'HAS_IDENTIFIER'
                }) as g
                CALL gds.nodeSimilarity.write('similarity', {
                        topK:15,
                        similarityMetric: 'JACCARD',
                        writeProperty: 'score',
                        writeRelationshipType:'SIMILAR_TO'
                    })
                YIELD nodesCompared, relationshipsWritten
                RETURN nodesCompared, relationshipsWritten as bi_rels
            """.trimIndent(),
            DbType.MEMGRAPH to
            """
                MATCH p=(c:Client)-[r:HAS_EMAIL|:HAS_PHONE|:HAS_SSN]-(id)
                WHERE c.firstPartyFraudGroup IS NOT NULL
                WITH project(p) as g
                CALL node_similarity.jaccard(g) 
                YIELD node1, node2, similarity
                WITH node1, node2, similarity
                WHERE similarity > 0
                CREATE (node1)-[r:SIMILAR_TO]->(node2)
                SET r.score=similarity
                RETURN 2*count(*) as bi_rels
            """.trimIndent()
        )

        val cleanSimilarToRels = dbByTypeQueries(
            DbType.NEO4J to "MATCH ()-[r:SIMILAR_TO]->() DELETE r",
            DbType.MEMGRAPH to "MATCH ()-[r:SIMILAR_TO]->() DELETE r",
        )

        repeat(100) {
            val result = testTimer.timeMeasureWithResult {
                session.runQuery(writeSimilarityQuery).rows.toList()
            }
            assertThat(result).isNotEmpty()
            assertThat(result.get(0).values.asLong("bi_rels")).isEqualTo(1518L)
            session.runQuery(cleanSimilarToRels)
        }
    }

    @AfterAll
    fun `remove created data`(session: Session) {
        val query = dbByTypeQueries(
            DbType.NEO4J to "MATCH ()-[r:SHARED_IDENTIFIERS]->() DELETE r",
            DbType.MEMGRAPH to "MATCH ()-[r:SHARED_IDENTIFIERS]->() DELETE r",
        )
        session.runQuery(query)
    }

}