package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.slf4j.LoggerFactory

class CrimeTotals : PoleTestBase() {

    private val log = LoggerFactory.getLogger(CrimeTotals::class.java)

    override fun performTest(session: Session) {
        repeat(10) {
            val result = session.runQuery("""
            MATCH (c:Crime)
            RETURN c.type AS crime_type, count(c) AS total
            ORDER BY count(c) DESC
        """.trimIndent())
            assert(result.rows.toList().isNotEmpty()) { "Result should not be empty" }
        }
//        logResult(log, result)
    }

}