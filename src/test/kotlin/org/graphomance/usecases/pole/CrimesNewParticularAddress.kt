package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class CrimesNewParticularAddress : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `crimes near to particular address`(session: Session) {
        val result = session.runQuery(
            """
            MATCH (l:Location {address: '1 Coronation Street', postcode: 'M5 3RW'})
            WITH point(l) AS corrie
            MATCH (x:Location)-[:HAS_POSTCODE]->(p:PostCode),
            (x)<-[:OCCURRED_AT]-(c:Crime)
            WITH x, p, c, point.distance(point(x), corrie) AS distance
            WHERE distance < 500
            RETURN x.address AS address, p.code AS postcode, count(c) AS crime_total, collect(distinct(c.type)) AS crime_type, distance
            ORDER BY distance
            LIMIT 10
            """.trimIndent()
        ).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}