package org.graphomance.usecases.pole

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.Test

class PersonKnowSubgraphAlgorithms : PoleTestBase() {

    @Test
    fun `betweenness centrality`(session: Session, testTimer: QueryTimer) {
        val query = when (session.getDbType()) {
            // undirected graph projection, without normalization, 4 threads
            DbType.NEO4J ->
                // In Neo4j projection can be created once and reused multiple times.
                // But it's not supported in Memgraph. So to be fair, the Neo4j query creates projection every time
                """
                    CALL {
                      CALL gds.graph.drop('social', false) YIELD graphName as _
                    }
                    CALL {
                      CALL gds.graph.project('social', 'Person', {KNOWS: {orientation:'UNDIRECTED'}}) YIELD graphName as _
                    } 
                    CALL gds.betweenness.stream('social', {concurrency: 4})
                    YIELD nodeId, score AS centrality
                    WITH gds.util.asNode(nodeId) AS node, centrality
                    RETURN node.name AS name, node.surname AS surname, node.nhs_no AS id, toInteger(centrality) AS score
                    ORDER BY centrality DESC
                    LIMIT 10;    
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH p=(:Person)-[:KNOWS]-()
                    WITH project(p) as persons_graph
                    CALL betweenness_centrality.get(persons_graph, false, false, 4) YIELD node, betweenness_centrality as centrality
                    RETURN node.name AS name, node.surname AS surname, node.nhs_no AS id, toInteger(centrality) AS score
                    ORDER BY centrality DESC
                    LIMIT 10;
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database type")
        }
        repeat(200) {
            val result = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList() }
            assertThat(result).hasSize(10)
            assertThat(result[0].values.asString("id")).isEqualTo("863-96-9468")
        }
    }

    /*
    Expected result
╒════════╤═══════════╤═════════════╤═════╕
│name    │surname    │id           │score│
╞════════╪═══════════╪═════════════╪═════╡
│"Annie" │"Duncan"   │"863-96-9468"│5275 │
├────────┼───────────┼─────────────┼─────┤
│"Ann"   │"Fox"      │"576-99-9244"│5116 │
├────────┼───────────┼─────────────┼─────┤
│"Amanda"│"Alexander"│"893-63-6176"│4599 │
├────────┼───────────┼─────────────┼─────┤
│"Bruce" │"Baker"    │"576-82-7408"│4193 │
├────────┼───────────┼─────────────┼─────┤
│"Andrew"│"Foster"   │"214-77-6416"│3693 │
├────────┼───────────┼─────────────┼─────┤
│"Anne"  │"Rice"     │"612-83-6356"│3418 │
├────────┼───────────┼─────────────┼─────┤
│"Alan"  │"Hicks"    │"852-52-0933"│3347 │
├────────┼───────────┼─────────────┼─────┤
│"Amy"   │"Murphy"   │"367-54-3328"│3282 │
├────────┼───────────┼─────────────┼─────┤
│"Adam"  │"Bradley"  │"237-02-1263"│3275 │
├────────┼───────────┼─────────────┼─────┤
│"Arthur"│"Willis"   │"271-78-8919"│3259 │
└────────┴───────────┴─────────────┴─────┘
     */
}