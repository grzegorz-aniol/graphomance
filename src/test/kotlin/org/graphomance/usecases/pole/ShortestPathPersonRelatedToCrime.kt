package org.graphomance.usecases.pole

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.engine.TestTimer
import org.junit.jupiter.api.Test

class ShortestPathPersonRelatedToCrime : PoleTestBase() {

    @Test
    fun `shortest path between persons related to crimes`(session: Session, testTimer: TestTimer) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
                """
                    MATCH (c:Crime {last_outcome: 'Under investigation', type: 'Drugs'})-[:INVESTIGATED_BY]->(:Officer {badge_no: '26-5234182'}),
                    (c)<-[:PARTY_TO]-(p:Person)
                    WITH COLLECT(p) AS persons
                    UNWIND persons AS p1
                    UNWIND persons AS p2
                    WITH * WHERE id(p1) < id(p2)
                    MATCH path = allshortestpaths((p1)-[:KNOWS|KNOWS_LW|KNOWS_SN|FAMILY_REL|KNOWS_PHONE*..3]-(p2))
                    RETURN path                    
                """.trimIndent()
            DbType.MEMGRAPH ->
                """
                    MATCH (c:Crime {last_outcome: 'Under investigation', type: 'Drugs'})-[:INVESTIGATED_BY]->(:Officer {badge_no: '26-5234182'}),
                    (c)<-[:PARTY_TO]-(p:Person)
                    WITH COLLECT(p) AS persons
                    UNWIND persons AS p1
                    UNWIND persons AS p2
                    WITH * WHERE id(p1) < id(p2)
                    MATCH path = (p1)-[:KNOWS|KNOWS_LW|KNOWS_SN|FAMILY_REL|KNOWS_PHONE * ALLSHORTEST 3 (r, p1 | 1)]-(p2)
                    RETURN path                    
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database")
        }

        repeat(100) {
            val size = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList().size }
            assertThat(size).isEqualTo(48)
        }
    }

    /** *
     * NEO4j results 48
     */
}