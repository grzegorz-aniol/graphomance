package org.graphomance.usecases.pole

import org.graphomance.api.Session
import org.junit.jupiter.api.RepeatedTest

class Top5VulnerablePeople : PoleTestBase() {

    @RepeatedTest(value = 100)
    fun `get top 5 vulnerable people`(session: Session) {
        val result = session.runQuery(
            """
                MATCH (p:Person)-[:KNOWS]-(friend)-[:PARTY_TO]->(:Crime)
                WHERE NOT (p:Person)-[:PARTY_TO]->(:Crime)
                RETURN p.name AS name, p.surname AS surname, p.nhs_no AS id, count(distinct friend) AS dangerousFriends
                ORDER BY dangerousFriends DESC
                LIMIT 5
        """.trimIndent()
        ).rows.toList()
        assert(result.isNotEmpty()) { "Result should not be empty" }
    }

}