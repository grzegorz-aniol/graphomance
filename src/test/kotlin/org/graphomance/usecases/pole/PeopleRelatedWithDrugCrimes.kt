package org.graphomance.usecases.pole

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.Session
import org.graphomance.engine.TestTimer
import org.junit.jupiter.api.Test

class PeopleRelatedWithDrugCrimes : PoleTestBase() {

    @Test
    fun `other related people associated with drugs crimes`(session: Session, testTimer: TestTimer) {
        val query = """
            MATCH path = (:Officer {badge_no: '26-5234182'})<-[:INVESTIGATED_BY]-(:Crime {type: 'Drugs'})<-[:PARTY_TO]-(:Person)-[:KNOWS*..3]-(:Person)-[:PARTY_TO]->(:Crime {type: 'Drugs'})
            RETURN path
        """.trimIndent()
        repeat(100) {
            val size = testTimer.timeMeasureWithResult { session.runQuery(query, emptyMap()).rows.toList().size }
            assertThat(size).isEqualTo(90)
        }
    }

}