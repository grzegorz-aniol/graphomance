package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class TopLocationsForCrimes : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `get top locations for crimes`(session: Session) {
        val result = session.runQuery(
            """
                MATCH (l:Location)<-[:OCCURRED_AT]-(:Crime)
                RETURN l.address AS address, l.postcode AS postcode, count(l) AS total
                ORDER BY count(l) DESC
                LIMIT 15
        """.trimIndent()
        ).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}