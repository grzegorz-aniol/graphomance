package org.graphomance.usecases.pole

import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.TestTimer
import org.junit.jupiter.api.Test

class Top5VulnerablePeople : PoleTestBase() {

    //Memgraph: Not yet implemented: atom expression 'WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)'

    @Test
    fun `get top 5 vulnerable people`(session: Session, testTimer: TestTimer) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (p:Person)-[:KNOWS]-(friend)-[:PARTY_TO]->(:Crime)
                    WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)
                    RETURN p.name AS name, p.surname AS surname, p.nhs_no AS id, count(distinct friend) AS dangerousFriends
                    ORDER BY dangerousFriends DESC
                    LIMIT 5                    
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
                    RETURN p.name AS name, p.surname AS surname, p.nhs_no AS id, count(distinct friend) AS dangerousFriends
                    ORDER BY dangerousFriends DESC
                    LIMIT 5                                        
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database type")
        }
        repeat(100) {
            val result = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList() }
            assert(result.isNotEmpty()) { "Result should not be empty" }
        }
    }

}