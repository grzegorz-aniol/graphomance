package org.graphomance.usecases.pole

import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class TopLocationsForCrimes : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `get top locations for crimes`(session: Session) {
        //TODO: probably same query can be used for Neo4j and Memgraph
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (l:Location)<-[:OCCURRED_AT]-(:Crime)
                    RETURN l.address AS address, l.postcode AS postcode, count(l) AS total
                    ORDER BY total DESC
                    LIMIT 15                
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH (l:Location)<-[:OCCURRED_AT]-(:Crime)
                    RETURN l.address AS address, l.postcode AS postcode, count(l) AS total
                    ORDER BY total DESC
                    LIMIT 15                                   
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database")
        }
        val result = session.runQuery(query).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}