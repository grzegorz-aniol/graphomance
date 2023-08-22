package org.graphomance.usecases.pole

import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class CrimesNewParticularAddress : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `crimes near to particular address`(session: Session) {
        val query = when (session.getDbType()) {
            DbType.NEO4J ->
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
            DbType.MEMGRAPH ->
                """
                    MATCH (l:Location {address: '1 Coronation Street', postcode: 'M5 3RW'})
                    MATCH (x:Location)-[:HAS_POSTCODE]->(p:PostCode),
                    (x)<-[:OCCURRED_AT]-(c:Crime)
                    CALL distance_calculator.single(x, l) yield distance
                    WITH p, c, x, l, distance
                    WHERE distance < 500
                    RETURN x.address AS address, p.code AS postcode, count(c) AS crime_total, collect(distinct(c.type)) AS crime_type, distance
                    ORDER BY distance
                    LIMIT 10                  
                """.trimIndent()
            else -> throw RuntimeException("Unsupported database")
        }
        val result = session.runQuery(query).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}