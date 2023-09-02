NodeSimpleCrud STANDARD_ERROR
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

NodeSimpleCrud STANDARD_OUT
    9/2/23, 12:02:43 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 232.58 calls/second
                   min = 1.02 milliseconds
                   max = 165.73 milliseconds
                  mean = 1.96 milliseconds
                stddev = 5.20 milliseconds
                median = 1.65 milliseconds
                  95% <= 2.83 milliseconds
                  99% <= 4.36 milliseconds
    NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 1068.33 calls/second
                   min = 0.66 milliseconds
                   max = 20.37 milliseconds
                  mean = 0.91 milliseconds
                stddev = 0.65 milliseconds
                median = 0.84 milliseconds
                  95% <= 1.23 milliseconds
                  99% <= 1.48 milliseconds
    NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 442.61 calls/second
                   min = 0.48 milliseconds
                   max = 14.95 milliseconds
                  mean = 1.31 milliseconds
                stddev = 0.56 milliseconds
                median = 1.23 milliseconds
                  95% <= 1.85 milliseconds
                  99% <= 2.24 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/2/23, 12:02:48 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 275.85 calls/second
                   min = 0.74 milliseconds
                   max = 6.57 milliseconds
                  mean = 1.13 milliseconds
                stddev = 0.36 milliseconds
                median = 1.05 milliseconds
                  95% <= 1.57 milliseconds
                  99% <= 1.96 milliseconds
    RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 621.66 calls/second
                   min = 0.63 milliseconds
                   max = 4.20 milliseconds
                  mean = 0.85 milliseconds
                stddev = 0.18 milliseconds
                median = 0.79 milliseconds
                  95% <= 1.15 milliseconds
                  99% <= 1.25 milliseconds
    RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 401.37 calls/second
                   min = 0.45 milliseconds
                   max = 5.79 milliseconds
                  mean = 0.87 milliseconds
                stddev = 0.22 milliseconds
                median = 0.82 milliseconds
                  95% <= 1.18 milliseconds
                  99% <= 1.32 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/2/23, 12:02:49 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 265.54 calls/second
                   min = 2.66 milliseconds
                   max = 12.73 milliseconds
                  mean = 3.71 milliseconds
                stddev = 1.23 milliseconds
                median = 3.33 milliseconds
                  95% <= 4.53 milliseconds
                  99% <= 9.40 milliseconds



CrimeTotals STANDARD_OUT
    9/2/23, 12:02:50 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 72.01 calls/second
                   min = 11.14 milliseconds
                   max = 39.50 milliseconds
                  mean = 13.86 milliseconds
                stddev = 3.84 milliseconds
                median = 12.77 milliseconds
                  95% <= 21.03 milliseconds
                  99% <= 25.48 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/2/23, 12:02:51 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 845.48 calls/second
                   min = 0.90 milliseconds
                   max = 5.89 milliseconds
                  mean = 1.17 milliseconds
                stddev = 0.30 milliseconds
                median = 1.11 milliseconds
                  95% <= 1.66 milliseconds
                  99% <= 2.03 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/2/23, 12:02:56 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 20.32 calls/second
                   min = 45.34 milliseconds
                   max = 83.17 milliseconds
                  mean = 49.18 milliseconds
                stddev = 4.96 milliseconds
                median = 47.74 milliseconds
                  95% <= 56.58 milliseconds
                  99% <= 83.17 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/2/23, 12:02:56 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 30
             mean rate = 225.93 calls/second
                   min = 3.06 milliseconds
                   max = 6.88 milliseconds
                  mean = 4.38 milliseconds
                stddev = 0.81 milliseconds
                median = 4.36 milliseconds
                  95% <= 6.83 milliseconds
                  99% <= 6.88 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/2/23, 12:03:00 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 49.51 calls/second
                   min = 14.83 milliseconds
                   max = 62.70 milliseconds
                  mean = 20.13 milliseconds
                stddev = 4.85 milliseconds
                median = 19.08 milliseconds
                  95% <= 26.49 milliseconds
                  99% <= 41.28 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/2/23, 12:03:00 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 472.17 calls/second
                   min = 1.69 milliseconds
                   max = 6.44 milliseconds
                  mean = 2.09 milliseconds
                stddev = 0.56 milliseconds
                median = 1.97 milliseconds
                  95% <= 2.49 milliseconds
                  99% <= 4.82 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/2/23, 12:03:01 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 519.87 calls/second
                   min = 1.42 milliseconds
                   max = 6.17 milliseconds
                  mean = 1.90 milliseconds
                stddev = 0.69 milliseconds
                median = 1.67 milliseconds
                  95% <= 2.53 milliseconds
                  99% <= 6.00 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/2/23, 12:03:04 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 32.72 calls/second
                   min = 25.69 milliseconds
                   max = 68.27 milliseconds
                  mean = 30.49 milliseconds
                stddev = 5.79 milliseconds
                median = 28.96 milliseconds
                  95% <= 38.92 milliseconds
                  99% <= 49.51 milliseconds



Gradle Test Executor 6 finished executing tests.

> Task :test
Finished generating test XML results (0.001 secs) into: /home/gangel/Personal/sources/graphomance/build/test-results/test
Generating HTML test report...
Finished generating test html results (0.002 secs) into: /home/gangel/Personal/sources/graphomance/build/reports/tests/test
Watching 55 directories to track changes
Watching 60 directories to track changes
Watching 61 directories to track changes

BUILD SUCCESSFUL in 26s
3 actionable tasks: 1 executed, 2 up-to-date
