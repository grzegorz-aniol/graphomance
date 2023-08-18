package org.gangel.graphomance.usecases.pole

import org.gangel.graphomance.api.Session
import org.slf4j.LoggerFactory

class CrimeTotals : PoleTestBase() {

    private val log = LoggerFactory.getLogger(CrimeTotals::class.java)

    override fun performTest(session: Session) {
        val result = session.runQuery("""
            MATCH (c:Crime)
            RETURN c.type AS crime_type, count(c) AS total
            ORDER BY count(c) DESC
        """.trimIndent())
        assert(result.rows.toList().isNotEmpty()) { "Result should not be empty" }
        logResult(log, result)
    }

}