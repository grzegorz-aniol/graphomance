package org.gangel.graphomance.usecases.pole

import org.gangel.graphomance.api.Session
import org.gangel.graphomance.engine.Repeat
import org.slf4j.LoggerFactory

class CrimesNewParticularAddress : PoleTestBase() {

    private val log = LoggerFactory.getLogger(CrimesNewParticularAddress::class.java)

    override fun performTest(session: Session) {
        repeat(10) {
            val result = session.runQuery("""
                MATCH (l:Location {address: '1 Coronation Street', postcode: 'M5 3RW'})
                WITH point(l) AS corrie
                MATCH (x:Location)-[:HAS_POSTCODE]->(p:PostCode),
                (x)<-[:OCCURRED_AT]-(c:Crime)
                WITH x, p, c, point.distance(point(x), corrie) AS distance
                WHERE distance < 500
                RETURN x.address AS address, p.code AS postcode, count(c) AS crime_total, collect(distinct(c.type)) AS crime_type, distance
                ORDER BY distance
                LIMIT 10
        """.trimIndent())
            assert(result.rows.toList().isNotEmpty()) { "Result should not be empty" }
        }
//        logResult(log, result)
    }

}