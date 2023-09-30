NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 11:40:36 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 213.75 calls/second
                   min = 1.03 milliseconds
                   max = 191.66 milliseconds
                  mean = 2.10 milliseconds
                stddev = 5.97 milliseconds
                median = 1.85 milliseconds
                  95% <= 2.72 milliseconds
                  99% <= 3.76 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 832.77 calls/second
                   min = 0.73 milliseconds
                   max = 21.37 milliseconds
                  mean = 1.18 milliseconds
                stddev = 1.52 milliseconds
                median = 0.94 milliseconds
                  95% <= 1.43 milliseconds
                  99% <= 7.44 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 399.98 calls/second
                   min = 0.60 milliseconds
                   max = 7.25 milliseconds
                  mean = 1.28 milliseconds
                stddev = 0.31 milliseconds
                median = 1.22 milliseconds
                  95% <= 1.72 milliseconds
                  99% <= 2.00 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 11:40:41 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 270.92 calls/second
                   min = 0.78 milliseconds
                   max = 3.51 milliseconds
                  mean = 1.09 milliseconds
                stddev = 0.20 milliseconds
                median = 1.03 milliseconds
                  95% <= 1.46 milliseconds
                  99% <= 1.66 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 591.32 calls/second
                   min = 0.66 milliseconds
                   max = 12.92 milliseconds
                  mean = 0.86 milliseconds
                stddev = 0.40 milliseconds
                median = 0.80 milliseconds
                  95% <= 1.14 milliseconds
                  99% <= 1.23 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 385.68 calls/second
                   min = 0.34 milliseconds
                   max = 3.83 milliseconds
                  mean = 0.89 milliseconds
                stddev = 0.17 milliseconds
                median = 0.85 milliseconds
                  95% <= 1.19 milliseconds
                  99% <= 1.31 milliseconds



FirstPartyFraud STANDARD_OUT
    9/30/23, 11:41:10 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    fraud.FirstPartyFraud.Identify clients sharing PII
                 count = 100
             mean rate = 3.41 calls/second
                   min = 11.72 milliseconds
                   max = 40.56 milliseconds
                  mean = 15.50 milliseconds
                stddev = 5.71 milliseconds
                median = 13.45 milliseconds
                  95% <= 28.64 milliseconds
                  99% <= 39.00 milliseconds
    fraud.FirstPartyFraud.Identify groups of clients sharing PII - fraud rings
                 count = 100
             mean rate = 3.60 calls/second
                   min = 227.80 milliseconds
                   max = 300.30 milliseconds
                  mean = 240.19 milliseconds
                stddev = 13.92 milliseconds
                median = 233.53 milliseconds
                  95% <= 271.06 milliseconds
                  99% <= 300.30 milliseconds
    fraud.FirstPartyFraud.pairwise JACARD similarity scores
                 count = 100
             mean rate = 26.61 calls/second
                   min = 20.91 milliseconds
                   max = 38.84 milliseconds
                  mean = 24.01 milliseconds
                stddev = 3.49 milliseconds
                median = 22.76 milliseconds
                  95% <= 33.63 milliseconds
                  99% <= 38.84 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/30/23, 11:41:11 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 253.61 calls/second
                   min = 2.76 milliseconds
                   max = 8.29 milliseconds
                  mean = 3.90 milliseconds
                stddev = 0.83 milliseconds
                median = 3.95 milliseconds
                  95% <= 4.74 milliseconds
                  99% <= 7.03 milliseconds



CrimeTotals STANDARD_OUT
    9/30/23, 11:41:12 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 76.97 calls/second
                   min = 9.94 milliseconds
                   max = 35.10 milliseconds
                  mean = 12.95 milliseconds
                stddev = 3.92 milliseconds
                median = 12.00 milliseconds
                  95% <= 19.96 milliseconds
                  99% <= 31.34 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/30/23, 11:41:13 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 961.11 calls/second
                   min = 0.82 milliseconds
                   max = 5.31 milliseconds
                  mean = 1.03 milliseconds
                stddev = 0.24 milliseconds
                median = 0.99 milliseconds
                  95% <= 1.21 milliseconds
                  99% <= 1.72 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/30/23, 11:41:18 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 21.57 calls/second
                   min = 42.16 milliseconds
                   max = 78.04 milliseconds
                  mean = 46.36 milliseconds
                stddev = 4.99 milliseconds
                median = 44.42 milliseconds
                  95% <= 54.28 milliseconds
                  99% <= 78.04 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/30/23, 11:41:18 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 214.85 calls/second
                   min = 2.50 milliseconds
                   max = 7.05 milliseconds
                  mean = 4.62 milliseconds
                stddev = 1.18 milliseconds
                median = 4.87 milliseconds
                  95% <= 6.15 milliseconds
                  99% <= 6.81 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/30/23, 11:41:22 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 49.40 calls/second
                   min = 17.04 milliseconds
                   max = 29.82 milliseconds
                  mean = 20.18 milliseconds
                stddev = 1.96 milliseconds
                median = 19.77 milliseconds
                  95% <= 23.69 milliseconds
                  99% <= 28.02 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/30/23, 11:41:23 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 455.16 calls/second
                   min = 1.48 milliseconds
                   max = 11.17 milliseconds
                  mean = 1.91 milliseconds
                stddev = 1.00 milliseconds
                median = 1.74 milliseconds
                  95% <= 2.22 milliseconds
                  99% <= 5.08 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/30/23, 11:41:23 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 499.62 calls/second
                   min = 1.48 milliseconds
                   max = 5.58 milliseconds
                  mean = 1.98 milliseconds
                stddev = 0.61 milliseconds
                median = 1.73 milliseconds
                  95% <= 2.51 milliseconds
                  99% <= 5.36 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/30/23, 11:41:25 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 38.24 calls/second
                   min = 24.25 milliseconds
                   max = 37.39 milliseconds
                  mean = 26.11 milliseconds
                stddev = 2.48 milliseconds
                median = 25.06 milliseconds
                  95% <= 31.50 milliseconds
                  99% <= 37.19 milliseconds



Gradle Test Executor 9 finished executing tests.
