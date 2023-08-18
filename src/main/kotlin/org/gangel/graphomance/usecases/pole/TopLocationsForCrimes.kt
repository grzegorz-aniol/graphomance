package org.gangel.graphomance.usecases.pole

import org.gangel.graphomance.api.Session
import org.gangel.graphomance.engine.Repeat
import org.slf4j.LoggerFactory

class TopLocationsForCrimes : PoleTestBase() {

    private val log = LoggerFactory.getLogger(TopLocationsForCrimes::class.java)

    override fun performTest(session: Session) {
        repeat(10) {
            val result = session.runQuery("""
                MATCH (l:Location)<-[:OCCURRED_AT]-(:Crime)
                RETURN l.address AS address, l.postcode AS postcode, count(l) AS total
                ORDER BY count(l) DESC
                LIMIT 15
        """.trimIndent())
            assert(result.rows.toList().isNotEmpty()) { "Result should not be empty" }
        }
//        logResult(log, result)
    }

}