NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 11:46:36 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 478.13 calls/second
                   min = 0.44 milliseconds
                   max = 188.99 milliseconds
                  mean = 1.00 milliseconds
                stddev = 5.94 milliseconds
                median = 0.71 milliseconds
                  95% <= 1.40 milliseconds
                  99% <= 2.31 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 2100.86 calls/second
                   min = 0.33 milliseconds
                   max = 1.20 milliseconds
                  mean = 0.44 milliseconds
                stddev = 0.07 milliseconds
                median = 0.44 milliseconds
                  95% <= 0.53 milliseconds
                  99% <= 0.65 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 971.80 calls/second
                   min = 0.34 milliseconds
                   max = 2.61 milliseconds
                  mean = 0.53 milliseconds
                stddev = 0.11 milliseconds
                median = 0.52 milliseconds
                  95% <= 0.68 milliseconds
                  99% <= 0.80 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 11:49:10 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 6.51 calls/second
                   min = 0.42 milliseconds
                   max = 43.77 milliseconds
                  mean = 0.81 milliseconds
                stddev = 1.69 milliseconds
                median = 0.60 milliseconds
                  95% <= 1.30 milliseconds
                  99% <= 4.16 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 13.10 calls/second
                   min = 0.68 milliseconds
                   max = 123.82 milliseconds
                  mean = 76.23 milliseconds
                stddev = 5.77 milliseconds
                median = 74.55 milliseconds
                  95% <= 84.74 milliseconds
                  99% <= 101.13 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 6.54 calls/second
                   min = 1.00 milliseconds
                   max = 141.20 milliseconds
                  mean = 76.42 milliseconds
                stddev = 5.71 milliseconds
                median = 74.73 milliseconds
                  95% <= 84.43 milliseconds
                  99% <= 98.96 milliseconds



FirstPartyFraud STANDARD_OUT
    9/30/23, 11:51:14 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    fraud.FirstPartyFraud.Identify clients sharing PII
                 count = 100
             mean rate = 0.81 calls/second
                   min = 7.56 milliseconds
                   max = 18.11 milliseconds
                  mean = 10.02 milliseconds
                stddev = 2.15 milliseconds
                median = 9.54 milliseconds
                  95% <= 15.04 milliseconds
                  99% <= 18.11 milliseconds
    fraud.FirstPartyFraud.Identify groups of clients sharing PII - fraud rings
                 count = 100
             mean rate = 0.81 calls/second
                   min = 246.12 milliseconds
                   max = 298.75 milliseconds
                  mean = 259.20 milliseconds
                stddev = 13.26 milliseconds
                median = 253.11 milliseconds
                  95% <= 289.47 milliseconds
                  99% <= 298.75 milliseconds
    fraud.FirstPartyFraud.pairwise JACARD similarity scores
                 count = 100
             mean rate = 1.03 calls/second
                   min = 854.37 milliseconds
                   max = 1045.76 milliseconds
                  mean = 907.53 milliseconds
                stddev = 49.40 milliseconds
                median = 886.02 milliseconds
                  95% <= 1016.48 milliseconds
                  99% <= 1045.76 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/30/23, 11:51:15 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 117.98 calls/second
                   min = 7.12 milliseconds
                   max = 14.31 milliseconds
                  mean = 8.44 milliseconds
                stddev = 1.63 milliseconds
                median = 7.76 milliseconds
                  95% <= 13.06 milliseconds
                  99% <= 13.85 milliseconds



CrimeTotals STANDARD_OUT
    9/30/23, 11:51:16 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 72.20 calls/second
                   min = 11.38 milliseconds
                   max = 28.22 milliseconds
                  mean = 13.83 milliseconds
                stddev = 3.00 milliseconds
                median = 13.01 milliseconds
                  95% <= 23.83 milliseconds
                  99% <= 28.22 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/30/23, 11:51:17 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 1153.97 calls/second
                   min = 0.51 milliseconds
                   max = 17.09 milliseconds
                  mean = 0.86 milliseconds
                stddev = 0.80 milliseconds
                median = 0.73 milliseconds
                  95% <= 1.30 milliseconds
                  99% <= 2.38 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/30/23, 11:51:28 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 9.46 calls/second
                   min = 96.35 milliseconds
                   max = 133.94 milliseconds
                  mean = 105.78 milliseconds
                stddev = 9.94 milliseconds
                median = 100.61 milliseconds
                  95% <= 129.34 milliseconds
                  99% <= 133.94 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/30/23, 11:51:28 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 239.55 calls/second
                   min = 3.25 milliseconds
                   max = 6.54 milliseconds
                  mean = 4.15 milliseconds
                stddev = 0.59 milliseconds
                median = 4.02 milliseconds
                  95% <= 5.31 milliseconds
                  99% <= 5.40 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/30/23, 11:51:30 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 101.53 calls/second
                   min = 7.76 milliseconds
                   max = 14.72 milliseconds
                  mean = 9.81 milliseconds
                stddev = 1.23 milliseconds
                median = 9.80 milliseconds
                  95% <= 12.50 milliseconds
                  99% <= 13.93 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/30/23, 11:51:30 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 516.20 calls/second
                   min = 1.53 milliseconds
                   max = 11.56 milliseconds
                  mean = 1.91 milliseconds
                stddev = 1.04 milliseconds
                median = 1.76 milliseconds
                  95% <= 2.10 milliseconds
                  99% <= 4.90 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/30/23, 11:51:31 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 892.73 calls/second
                   min = 0.92 milliseconds
                   max = 3.64 milliseconds
                  mean = 1.10 milliseconds
                stddev = 0.35 milliseconds
                median = 0.99 milliseconds
                  95% <= 1.57 milliseconds
                  99% <= 2.62 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/30/23, 11:51:35 AM ===========================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 21.84 calls/second
                   min = 39.87 milliseconds
                   max = 64.08 milliseconds
                  mean = 45.72 milliseconds
                stddev = 3.37 milliseconds
                median = 45.15 milliseconds
                  95% <= 50.55 milliseconds
                  99% <= 56.31 milliseconds



Gradle Test Executor 11 finished executing tests.
