package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class CrimeTotals : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `count total number of crimes by type`(session: Session) {
        val result = session.runQuery(
            """
            MATCH (c:Crime)
            RETURN c.type AS crime_type, count(c) AS total
            ORDER BY total DESC
        """.trimIndent()
        ).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}