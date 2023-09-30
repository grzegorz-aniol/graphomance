NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 1:10:33 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 250.72 calls/second
                   min = 0.91 milliseconds
                   max = 286.93 milliseconds
                  mean = 1.90 milliseconds
                stddev = 9.01 milliseconds
                median = 1.49 milliseconds
                  95% <= 2.41 milliseconds
                  99% <= 3.61 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 1110.14 calls/second
                   min = 0.66 milliseconds
                   max = 6.17 milliseconds
                  mean = 0.88 milliseconds
                stddev = 0.21 milliseconds
                median = 0.85 milliseconds
                  95% <= 1.16 milliseconds
                  99% <= 1.29 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 495.23 calls/second
                   min = 0.52 milliseconds
                   max = 7.39 milliseconds
                  mean = 1.10 milliseconds
                stddev = 0.32 milliseconds
                median = 1.03 milliseconds
                  95% <= 1.52 milliseconds
                  99% <= 1.95 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 1:10:38 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 284.04 calls/second
                   min = 0.72 milliseconds
                   max = 2.67 milliseconds
                  mean = 1.15 milliseconds
                stddev = 0.24 milliseconds
                median = 1.11 milliseconds
                  95% <= 1.60 milliseconds
                  99% <= 1.82 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 657.95 calls/second
                   min = 0.58 milliseconds
                   max = 5.21 milliseconds
                  mean = 0.81 milliseconds
                stddev = 0.20 milliseconds
                median = 0.76 milliseconds
                  95% <= 1.09 milliseconds
                  99% <= 1.19 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 422.97 calls/second
                   min = 0.33 milliseconds
                   max = 5.57 milliseconds
                  mean = 0.84 milliseconds
                stddev = 0.20 milliseconds
                median = 0.79 milliseconds
                  95% <= 1.13 milliseconds
                  99% <= 1.23 milliseconds



FirstPartyFraud STANDARD_OUT
    9/30/23, 1:11:08 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    fraud.FirstPartyFraud.Identify clients sharing PII
                 count = 100
             mean rate = 3.30 calls/second
                   min = 11.30 milliseconds
                   max = 22.31 milliseconds
                  mean = 12.99 milliseconds
                stddev = 1.85 milliseconds
                median = 12.46 milliseconds
                  95% <= 16.63 milliseconds
                  99% <= 19.98 milliseconds
    fraud.FirstPartyFraud.Identify groups of clients sharing PII - fraud rings
                 count = 100
             mean rate = 3.45 calls/second
                   min = 240.95 milliseconds
                   max = 324.16 milliseconds
                  mean = 253.52 milliseconds
                stddev = 18.80 milliseconds
                median = 244.85 milliseconds
                  95% <= 301.06 milliseconds
                  99% <= 309.63 milliseconds
    fraud.FirstPartyFraud.pairwise JACARD similarity scores
                 count = 100
             mean rate = 27.76 calls/second
                   min = 20.06 milliseconds
                   max = 56.59 milliseconds
                  mean = 23.27 milliseconds
                stddev = 4.07 milliseconds
                median = 21.95 milliseconds
                  95% <= 28.35 milliseconds
                  99% <= 31.00 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/30/23, 1:11:09 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 319.51 calls/second
                   min = 2.01 milliseconds
                   max = 9.51 milliseconds
                  mean = 3.08 milliseconds
                stddev = 0.94 milliseconds
                median = 2.84 milliseconds
                  95% <= 4.01 milliseconds
                  99% <= 6.49 milliseconds



CrimeTotals STANDARD_OUT
    9/30/23, 1:11:10 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 88.92 calls/second
                   min = 9.51 milliseconds
                   max = 18.14 milliseconds
                  mean = 11.21 milliseconds
                stddev = 1.73 milliseconds
                median = 10.58 milliseconds
                  95% <= 14.68 milliseconds
                  99% <= 17.71 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/30/23, 1:11:11 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 874.97 calls/second
                   min = 0.83 milliseconds
                   max = 15.32 milliseconds
                  mean = 1.13 milliseconds
                stddev = 0.59 milliseconds
                median = 1.04 milliseconds
                  95% <= 1.72 milliseconds
                  99% <= 2.35 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/30/23, 1:11:15 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 22.84 calls/second
                   min = 40.74 milliseconds
                   max = 62.69 milliseconds
                  mean = 43.76 milliseconds
                stddev = 3.11 milliseconds
                median = 42.82 milliseconds
                  95% <= 49.29 milliseconds
                  99% <= 62.69 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/30/23, 1:11:16 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 208.66 calls/second
                   min = 3.40 milliseconds
                   max = 8.33 milliseconds
                  mean = 4.76 milliseconds
                stddev = 1.05 milliseconds
                median = 4.45 milliseconds
                  95% <= 6.60 milliseconds
                  99% <= 7.82 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/30/23, 1:11:20 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 52.96 calls/second
                   min = 15.89 milliseconds
                   max = 38.22 milliseconds
                  mean = 18.80 milliseconds
                stddev = 2.37 milliseconds
                median = 18.35 milliseconds
                  95% <= 21.66 milliseconds
                  99% <= 25.80 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/30/23, 1:11:20 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 518.94 calls/second
                   min = 1.34 milliseconds
                   max = 9.27 milliseconds
                  mean = 1.69 milliseconds
                stddev = 0.88 milliseconds
                median = 1.55 milliseconds
                  95% <= 2.07 milliseconds
                  99% <= 5.49 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/30/23, 1:11:20 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 554.07 calls/second
                   min = 1.41 milliseconds
                   max = 4.91 milliseconds
                  mean = 1.78 milliseconds
                stddev = 0.55 milliseconds
                median = 1.57 milliseconds
                  95% <= 2.61 milliseconds
                  99% <= 4.63 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/30/23, 1:11:23 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 37.56 calls/second
                   min = 23.93 milliseconds
                   max = 70.41 milliseconds
                  mean = 26.56 milliseconds
                stddev = 5.45 milliseconds
                median = 24.71 milliseconds
                  95% <= 32.67 milliseconds
                  99% <= 43.27 milliseconds



Gradle Test Executor 24 finished executing tests.
