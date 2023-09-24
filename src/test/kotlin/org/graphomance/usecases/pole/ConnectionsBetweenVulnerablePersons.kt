package org.graphomance.usecases.pole

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.Test

class ConnectionsBetweenVulnerablePersons : PoleTestBase() {

    //Memgraph: Not yet implemented: atom expression 'WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)'
    @Test
    fun `shortest connections between vulnerable persons`(session: Session, testTimer: QueryTimer) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (p:Person)-[:KNOWS]-(friend)-[:PARTY_TO]->(:Crime)
                    WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)
                    WITH p, count(distinct friend) AS dangerousFriends
                    ORDER BY dangerousFriends DESC
                    LIMIT 5
                    WITH COLLECT (p) AS people
                    UNWIND people AS p1
                    UNWIND people AS p2
                    WITH * WHERE id(p1) <> id (p2)
                    MATCH path = shortestpath((p1)-[:KNOWS*]-(p2))
                    RETURN path
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH (p:Person)-[:KNOWS]-(friend)-[:PARTY_TO]->(:Crime)
                    CALL {
                      WITH p
                      MATCH (:Crime)<-[:PARTY_TO]-(p)
                      RETURN count(*) as cnt 
                    }
                    
                    WITH p, cnt, friend
                    WHERE cnt=0
                    
                    WITH p, count(distinct friend) AS dangerousFriends
                    ORDER BY dangerousFriends DESC
                    LIMIT 5
                    
                    WITH COLLECT (p) AS people
                    UNWIND people AS p1
                    UNWIND people AS p2
                    
                    WITH * WHERE id(p1) <> id (p2)
                    MATCH path = (p1)-[:KNOWS * wShortest (r, p1 | 1)]-(p2)
                    
                    RETURN path
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database type")
        }
        repeat(100) {
            val size = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList().size }
            assertThat(size).isEqualTo(20)
        }
    }

}