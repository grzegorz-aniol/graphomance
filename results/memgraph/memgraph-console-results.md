NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 1:15:43 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 465.78 calls/second
                   min = 0.47 milliseconds
                   max = 167.68 milliseconds
                  mean = 1.01 milliseconds
                stddev = 5.26 milliseconds
                median = 0.72 milliseconds
                  95% <= 1.58 milliseconds
                  99% <= 2.64 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 2208.71 calls/second
                   min = 0.33 milliseconds
                   max = 1.24 milliseconds
                  mean = 0.43 milliseconds
                stddev = 0.07 milliseconds
                median = 0.41 milliseconds
                  95% <= 0.53 milliseconds
                  99% <= 0.63 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 948.15 calls/second
                   min = 0.41 milliseconds
                   max = 1.97 milliseconds
                  mean = 0.57 milliseconds
                stddev = 0.10 milliseconds
                median = 0.56 milliseconds
                  95% <= 0.72 milliseconds
                  99% <= 0.91 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 1:17:32 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 9.20 calls/second
                   min = 0.40 milliseconds
                   max = 1.31 milliseconds
                  mean = 0.52 milliseconds
                stddev = 0.07 milliseconds
                median = 0.52 milliseconds
                  95% <= 0.62 milliseconds
                  99% <= 0.73 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 18.60 calls/second
                   min = 0.82 milliseconds
                   max = 86.27 milliseconds
                  mean = 53.61 milliseconds
                stddev = 3.67 milliseconds
                median = 52.68 milliseconds
                  95% <= 59.40 milliseconds
                  99% <= 70.12 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 9.25 calls/second
                   min = 1.18 milliseconds
                   max = 108.75 milliseconds
                  mean = 54.11 milliseconds
                stddev = 5.26 milliseconds
                median = 52.72 milliseconds
                  95% <= 62.43 milliseconds
                  99% <= 80.89 milliseconds



FirstPartyFraud STANDARD_OUT
    9/30/23, 1:19:31 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    fraud.FirstPartyFraud.Identify clients sharing PII
                 count = 100
             mean rate = 0.85 calls/second
                   min = 8.03 milliseconds
                   max = 15.71 milliseconds
                  mean = 9.66 milliseconds
                stddev = 1.31 milliseconds
                median = 9.37 milliseconds
                  95% <= 11.47 milliseconds
                  99% <= 14.34 milliseconds
    fraud.FirstPartyFraud.Identify groups of clients sharing PII - fraud rings
                 count = 100
             mean rate = 0.85 calls/second
                   min = 201.94 milliseconds
                   max = 266.95 milliseconds
                  mean = 210.09 milliseconds
                stddev = 10.40 milliseconds
                median = 206.99 milliseconds
                  95% <= 227.82 milliseconds
                  99% <= 257.52 milliseconds
    fraud.FirstPartyFraud.pairwise JACARD similarity scores
                 count = 100
             mean rate = 1.04 calls/second
                   min = 881.00 milliseconds
                   max = 1078.54 milliseconds
                  mean = 923.51 milliseconds
                stddev = 41.26 milliseconds
                median = 903.78 milliseconds
                  95% <= 1010.61 milliseconds
                  99% <= 1049.67 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/30/23, 1:19:32 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 112.34 calls/second
                   min = 7.27 milliseconds
                   max = 13.90 milliseconds
                  mean = 8.87 milliseconds
                stddev = 1.81 milliseconds
                median = 8.01 milliseconds
                  95% <= 13.50 milliseconds
                  99% <= 13.83 milliseconds



CrimeTotals STANDARD_OUT
    9/30/23, 1:19:33 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 101.39 calls/second
                   min = 8.34 milliseconds
                   max = 16.88 milliseconds
                  mean = 9.84 milliseconds
                stddev = 1.70 milliseconds
                median = 9.13 milliseconds
                  95% <= 13.59 milliseconds
                  99% <= 16.88 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/30/23, 1:19:33 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 1863.59 calls/second
                   min = 0.36 milliseconds
                   max = 3.64 milliseconds
                  mean = 0.53 milliseconds
                stddev = 0.19 milliseconds
                median = 0.50 milliseconds
                  95% <= 0.78 milliseconds
                  99% <= 0.87 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/30/23, 1:19:45 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 8.74 calls/second
                   min = 107.67 milliseconds
                   max = 165.12 milliseconds
                  mean = 114.32 milliseconds
                stddev = 8.74 milliseconds
                median = 111.21 milliseconds
                  95% <= 127.98 milliseconds
                  99% <= 152.01 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/30/23, 1:19:45 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 333.88 calls/second
                   min = 2.08 milliseconds
                   max = 5.77 milliseconds
                  mean = 2.98 milliseconds
                stddev = 0.68 milliseconds
                median = 2.72 milliseconds
                  95% <= 4.27 milliseconds
                  99% <= 4.75 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/30/23, 1:19:47 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 119.08 calls/second
                   min = 7.51 milliseconds
                   max = 14.38 milliseconds
                  mean = 8.37 milliseconds
                stddev = 0.64 milliseconds
                median = 8.42 milliseconds
                  95% <= 8.96 milliseconds
                  99% <= 9.36 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/30/23, 1:19:47 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 478.92 calls/second
                   min = 1.42 milliseconds
                   max = 28.08 milliseconds
                  mean = 2.07 milliseconds
                stddev = 2.67 milliseconds
                median = 1.63 milliseconds
                  95% <= 2.65 milliseconds
                  99% <= 4.68 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/30/23, 1:19:47 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 857.44 calls/second
                   min = 0.99 milliseconds
                   max = 3.59 milliseconds
                  mean = 1.15 milliseconds
                stddev = 0.29 milliseconds
                median = 1.09 milliseconds
                  95% <= 1.29 milliseconds
                  99% <= 2.38 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/30/23, 1:19:51 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 24.36 calls/second
                   min = 37.40 milliseconds
                   max = 58.61 milliseconds
                  mean = 41.01 milliseconds
                stddev = 3.47 milliseconds
                median = 39.87 milliseconds
                  95% <= 47.25 milliseconds
                  99% <= 58.61 milliseconds



Gradle Test Executor 26 finished executing tests.
