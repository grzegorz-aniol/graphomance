package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.graphomance.engine.TestTimer
import org.junit.jupiter.api.Test

class TopLocationsForCrimes : PoleTestBase() {

    @Test
    fun `get top locations for crimes`(session: Session, testTimer: TestTimer) {
        val query =
                """
                    MATCH (l:Location)<-[:OCCURRED_AT]-(:Crime)
                    RETURN l.address AS address, l.postcode AS postcode, count(l) AS total
                    ORDER BY total DESC
                    LIMIT 15                
                """.trimIndent()
        repeat(100) {
            val result = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList() }
            assert(result.isNotEmpty()) { "Result should not be empty" }
        }
    }

}