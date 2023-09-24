package org.graphomance.usecases.pole

import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.Session
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.Test

class CrimesInvestigatedBy : PoleTestBase() {

    @Test
    fun `crimes investigated by officer Larive - no indexes`(session: Session, testTimer: QueryTimer) {
        // TODO: check there is no any index available for the query

        val query = """
            MATCH (c:Crime {last_outcome: ${'$'}outcome})-[i:INVESTIGATED_BY]->(o:Officer {badge_no: ${'$'}badge, surname: ${'$'}surname})
            return *            
        """.trimIndent()
        val parameters = mapOf(
            "outcome" to "Under investigation",
            "badge" to "26-5234182",
            "surname" to "Larive",
        )
        repeat(1000) {
            val size = testTimer.timeMeasureWithResult { session.runQuery(query, parameters).rows.toList().size }
            assertThat(size).isEqualTo(8)
        }
    }

    /*
╒══════════════════════════════════════════════════════════════════════╤══════════════════╤══════════════════════════════════════════════════════════════════════╕
│c                                                                     │i                 │o                                                                     │
╞══════════════════════════════════════════════════════════════════════╪══════════════════╪══════════════════════════════════════════════════════════════════════╡
│(:Crime {date: "2/08/2017",id: "57117",type: "Drugs",last_outcome: "Un│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│der investigation"})                                                  │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "11/08/2017",id: "42494",type: "Public order",last_outc│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│ome: "Under investigation"})                                          │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "25/08/2017",id: "57116",type: "Drugs",last_outcome: "U│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│nder investigation"})                                                 │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "13/08/2017",id: "40664",type: "Vehicle crime",last_out│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│come: "Under investigation"})                                         │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "28/08/2017",id: "55555",type: "Criminal damage and ars│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│on",last_outcome: "Under investigation"})                             │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "15/08/2017",note: "this is an awesome new note ",id: "│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│31868",type: "Drugs",last_outcome: "Under investigation"})            │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "18/08/2017",id: "39397",type: "Other theft",last_outco│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│me: "Under investigation"})                                           │                  │ "Police Constable",id: "1522"})                                      │
├──────────────────────────────────────────────────────────────────────┼──────────────────┼──────────────────────────────────────────────────────────────────────┤
│(:Crime {date: "3/08/2017",id: "38831",type: "Other theft",last_outcom│[:INVESTIGATED_BY]│(:Officer {badge_no: "26-5234182",surname: "Larive",name: "Devy",rank:│
│e: "Under investigation"})                                            │                  │ "Police Constable",id: "1522"})                                      │
└──────────────────────────────────────────────────────────────────────┴──────────────────┴──────────────────────────────────────────────────────────────────────┘
     */
}