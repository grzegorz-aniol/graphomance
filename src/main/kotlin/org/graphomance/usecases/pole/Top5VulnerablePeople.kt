package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.slf4j.LoggerFactory

class Top5VulnerablePeople : PoleTestBase() {

    private val log = LoggerFactory.getLogger(Top5VulnerablePeople::class.java)

    override fun performTest(session: Session) {
        repeat(10) {
            val result = session.runQuery("""
                MATCH (p:Person)-[:KNOWS]-(friend)-[:PARTY_TO]->(:Crime)
                WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)
                RETURN p.name AS name, p.surname AS surname, p.nhs_no AS id, count(distinct friend) AS dangerousFriends
                ORDER BY dangerousFriends DESC
                LIMIT 5
        """.trimIndent())
            assert(result.rows.toList().isNotEmpty()) { "Result should not be empty" }
        }
//        logResult(log, result)
    }

}