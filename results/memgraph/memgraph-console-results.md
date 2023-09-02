NodeSimpleCrud STANDARD_ERROR
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/2/23, 8:25:57 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 420.44 calls/second
                   min = 0.50 milliseconds
                   max = 183.74 milliseconds
                  mean = 1.08 milliseconds
                stddev = 5.79 milliseconds
                median = 0.75 milliseconds
                  95% <= 1.68 milliseconds
                  99% <= 2.82 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 1963.26 calls/second
                   min = 0.32 milliseconds
                   max = 1.26 milliseconds
                  mean = 0.47 milliseconds
                stddev = 0.09 milliseconds
                median = 0.47 milliseconds
                  95% <= 0.61 milliseconds
                  99% <= 0.75 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 834.71 calls/second
                   min = 0.40 milliseconds
                   max = 13.08 milliseconds
                  mean = 0.66 milliseconds
                stddev = 0.64 milliseconds
                median = 0.59 milliseconds
                  95% <= 0.82 milliseconds
                  99% <= 2.08 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/2/23, 8:26:24 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 37.36 calls/second
                   min = 0.29 milliseconds
                   max = 1.35 milliseconds
                  mean = 0.44 milliseconds
                stddev = 0.08 milliseconds
                median = 0.43 milliseconds
                  95% <= 0.57 milliseconds
                  99% <= 0.62 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 75.19 calls/second
                   min = 0.66 milliseconds
                   max = 33.36 milliseconds
                  mean = 13.05 milliseconds
                stddev = 2.26 milliseconds
                median = 12.45 milliseconds
                  95% <= 16.72 milliseconds
                  99% <= 22.30 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 38.00 calls/second
                   min = 0.62 milliseconds
                   max = 26.92 milliseconds
                  mean = 12.99 milliseconds
                stddev = 1.72 milliseconds
                median = 12.64 milliseconds
                  95% <= 15.47 milliseconds
                  99% <= 20.93 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/2/23, 8:26:26 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 90.89 calls/second
                   min = 8.54 milliseconds
                   max = 20.26 milliseconds
                  mean = 10.94 milliseconds
                stddev = 2.56 milliseconds
                median = 10.10 milliseconds
                  95% <= 17.72 milliseconds
                  99% <= 19.21 milliseconds



CrimeTotals STANDARD_OUT
    9/2/23, 8:26:27 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 69.26 calls/second
                   min = 12.45 milliseconds
                   max = 19.55 milliseconds
                  mean = 14.41 milliseconds
                stddev = 1.26 milliseconds
                median = 14.20 milliseconds
                  95% <= 16.47 milliseconds
                  99% <= 18.94 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/2/23, 8:26:28 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 1308.98 calls/second
                   min = 0.48 milliseconds
                   max = 9.04 milliseconds
                  mean = 0.75 milliseconds
                stddev = 0.43 milliseconds
                median = 0.69 milliseconds
                  95% <= 1.09 milliseconds
                  99% <= 1.41 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/2/23, 8:26:38 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 9.34 calls/second
                   min = 97.64 milliseconds
                   max = 140.28 milliseconds
                  mean = 106.96 milliseconds
                stddev = 7.13 milliseconds
                median = 104.95 milliseconds
                  95% <= 119.74 milliseconds
                  99% <= 126.94 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/2/23, 8:26:39 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 100
             mean rate = 324.05 calls/second
                   min = 2.53 milliseconds
                   max = 6.14 milliseconds
                  mean = 3.07 milliseconds
                stddev = 0.59 milliseconds
                median = 2.84 milliseconds
                  95% <= 3.89 milliseconds
                  99% <= 5.92 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/2/23, 8:26:41 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 114.45 calls/second
                   min = 7.51 milliseconds
                   max = 14.23 milliseconds
                  mean = 8.68 milliseconds
                stddev = 0.89 milliseconds
                median = 8.60 milliseconds
                  95% <= 10.12 milliseconds
                  99% <= 13.14 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/2/23, 8:26:41 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 439.43 calls/second
                   min = 1.77 milliseconds
                   max = 11.97 milliseconds
                  mean = 2.25 milliseconds
                stddev = 1.05 milliseconds
                median = 2.06 milliseconds
                  95% <= 3.05 milliseconds
                  99% <= 4.10 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/2/23, 8:26:41 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 776.58 calls/second
                   min = 1.08 milliseconds
                   max = 3.34 milliseconds
                  mean = 1.27 milliseconds
                stddev = 0.35 milliseconds
                median = 1.15 milliseconds
                  95% <= 1.86 milliseconds
                  99% <= 2.98 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/2/23, 8:26:46 PM =============================================================

    -- Timers ----------------------------------------------------------------------
    pole.TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 20.86 calls/second
                   min = 42.05 milliseconds
                   max = 78.94 milliseconds
                  mean = 47.87 milliseconds
                stddev = 5.70 milliseconds
                median = 46.37 milliseconds
                  95% <= 56.09 milliseconds
                  99% <= 78.88 milliseconds



Gradle Test Executor 12 finished executing tests.
