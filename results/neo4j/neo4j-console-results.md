NodeSimpleCrud STANDARD_ERROR
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/2/23, 8:24:38 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 223.07 calls/second
                   min = 1.06 milliseconds
                   max = 209.09 milliseconds
                  mean = 2.01 milliseconds
                stddev = 6.58 milliseconds
                median = 1.64 milliseconds
                  95% <= 2.65 milliseconds
                  99% <= 5.27 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 1031.82 calls/second
                   min = 0.45 milliseconds
                   max = 16.16 milliseconds
                  mean = 0.95 milliseconds
                stddev = 0.89 milliseconds
                median = 0.85 milliseconds
                  95% <= 1.20 milliseconds
                  99% <= 1.62 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 419.94 calls/second
                   min = 0.59 milliseconds
                   max = 18.68 milliseconds
                  mean = 1.39 milliseconds
                stddev = 1.19 milliseconds
                median = 1.21 milliseconds
                  95% <= 1.90 milliseconds
                  99% <= 4.69 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/2/23, 8:24:43 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 270.50 calls/second
                   min = 0.73 milliseconds
                   max = 5.31 milliseconds
                  mean = 1.02 milliseconds
                stddev = 0.22 milliseconds
                median = 0.97 milliseconds
                  95% <= 1.34 milliseconds
                  99% <= 1.52 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 585.96 calls/second
                   min = 0.67 milliseconds
                   max = 17.58 milliseconds
                  mean = 0.95 milliseconds
                stddev = 1.07 milliseconds
                median = 0.82 milliseconds
                  95% <= 1.21 milliseconds
                  99% <= 1.57 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 374.12 calls/second
                   min = 0.37 milliseconds
                   max = 15.76 milliseconds
                  mean = 0.96 milliseconds
                stddev = 1.01 milliseconds
                median = 0.82 milliseconds
                  95% <= 1.20 milliseconds
                  99% <= 1.83 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/2/23, 8:24:43 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 328.16 calls/second
                   min = 2.14 milliseconds
                   max = 9.52 milliseconds
                  mean = 3.00 milliseconds
                stddev = 1.18 milliseconds
                median = 2.55 milliseconds
                  95% <= 5.35 milliseconds
                  99% <= 7.64 milliseconds



CrimeTotals STANDARD_OUT
    9/2/23, 8:24:45 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 72.88 calls/second
                   min = 11.25 milliseconds
                   max = 28.40 milliseconds
                  mean = 13.68 milliseconds
                stddev = 2.62 milliseconds
                median = 13.06 milliseconds
                  95% <= 20.04 milliseconds
                  99% <= 22.99 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/2/23, 8:24:46 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 783.92 calls/second
                   min = 0.97 milliseconds
                   max = 10.35 milliseconds
                  mean = 1.27 milliseconds
                stddev = 0.41 milliseconds
                median = 1.19 milliseconds
                  95% <= 1.74 milliseconds
                  99% <= 2.00 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/2/23, 8:24:51 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 19.63 calls/second
                   min = 45.57 milliseconds
                   max = 62.83 milliseconds
                  mean = 50.89 milliseconds
                stddev = 3.42 milliseconds
                median = 50.32 milliseconds
                  95% <= 56.42 milliseconds
                  99% <= 60.01 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/2/23, 8:24:51 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 247.13 calls/second
                   min = 2.80 milliseconds
                   max = 50.95 milliseconds
                  mean = 4.02 milliseconds
                stddev = 4.79 milliseconds
                median = 3.29 milliseconds
                  95% <= 5.26 milliseconds
                  99% <= 9.27 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/2/23, 8:24:55 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 53.76 calls/second
                   min = 14.01 milliseconds
                   max = 37.08 milliseconds
                  mean = 18.52 milliseconds
                stddev = 2.44 milliseconds
                median = 18.42 milliseconds
                  95% <= 21.81 milliseconds
                  99% <= 30.02 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/2/23, 8:24:55 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 532.31 calls/second
                   min = 1.45 milliseconds
                   max = 5.21 milliseconds
                  mean = 1.85 milliseconds
                stddev = 0.47 milliseconds
                median = 1.78 milliseconds
                  95% <= 2.07 milliseconds
                  99% <= 4.76 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/2/23, 8:24:56 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 470.72 calls/second
                   min = 1.52 milliseconds
                   max = 5.78 milliseconds
                  mean = 2.10 milliseconds
                stddev = 0.62 milliseconds
                median = 2.11 milliseconds
                  95% <= 2.71 milliseconds
                  99% <= 5.51 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/2/23, 8:24:59 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 33.47 calls/second
                   min = 25.58 milliseconds
                   max = 67.88 milliseconds
                  mean = 29.82 milliseconds
                stddev = 6.89 milliseconds
                median = 27.93 milliseconds
                  95% <= 39.66 milliseconds
                  99% <= 67.50 milliseconds



Gradle Test Executor 10 finished executing tests.
