package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.Test

class CrimeTotals : PoleTestBase() {

    @Test
    fun `count total number of crimes by type`(session: Session, testTimer: QueryTimer) {
        val query =
            """
            MATCH (c:Crime)
            RETURN c.type AS crime_type, count(c) AS total
            ORDER BY total DESC
        """.trimIndent()
        repeat(100) {
            val result = testTimer.timeMeasureWithResult { session.runQuery(query).rows.toList() }
            assert(result.isNotEmpty()) { "Result should not be empty" }
        }
    }

}