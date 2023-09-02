NodeSimpleCrud STANDARD_ERROR
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

NodeSimpleCrud STANDARD_OUT
    9/2/23, 12:04:01 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 401.11 calls/second
                   min = 0.53 milliseconds
                   max = 184.58 milliseconds
                  mean = 1.15 milliseconds
                stddev = 5.84 milliseconds
                median = 0.79 milliseconds
                  95% <= 1.87 milliseconds
                  99% <= 3.24 milliseconds
    NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 1837.26 calls/second
                   min = 0.31 milliseconds
                   max = 11.37 milliseconds
                  mean = 0.50 milliseconds
                stddev = 0.49 milliseconds
                median = 0.45 milliseconds
                  95% <= 0.60 milliseconds
                  99% <= 1.16 milliseconds
    NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 793.17 calls/second
                   min = 0.42 milliseconds
                   max = 16.98 milliseconds
                  mean = 0.70 milliseconds
                stddev = 0.64 milliseconds
                median = 0.62 milliseconds
                  95% <= 0.91 milliseconds
                  99% <= 2.79 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/2/23, 12:04:27 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 38.09 calls/second
                   min = 0.32 milliseconds
                   max = 19.49 milliseconds
                  mean = 0.59 milliseconds
                stddev = 0.72 milliseconds
                median = 0.52 milliseconds
                  95% <= 0.70 milliseconds
                  99% <= 1.64 milliseconds
    RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 78.06 calls/second
                   min = 0.64 milliseconds
                   max = 23.35 milliseconds
                  mean = 12.63 milliseconds
                stddev = 1.62 milliseconds
                median = 12.22 milliseconds
                  95% <= 15.23 milliseconds
                  99% <= 20.22 milliseconds
    RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 38.97 calls/second
                   min = 1.06 milliseconds
                   max = 22.59 milliseconds
                  mean = 12.82 milliseconds
                stddev = 1.73 milliseconds
                median = 12.38 milliseconds
                  95% <= 15.77 milliseconds
                  99% <= 20.65 milliseconds



ConnectionsBetweenVulnerablePersons STANDARD_OUT
    9/2/23, 12:04:28 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    ConnectionsBetweenVulnerablePersons.shortest connections between vulnerable persons
                 count = 100
             mean rate = 105.38 calls/second
                   min = 7.98 milliseconds
                   max = 15.15 milliseconds
                  mean = 9.44 milliseconds
                stddev = 1.87 milliseconds
                median = 8.80 milliseconds
                  95% <= 14.72 milliseconds
                  99% <= 15.15 milliseconds



CrimeTotals STANDARD_OUT
    9/2/23, 12:04:30 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimeTotals.count total number of crimes by type
                 count = 100
             mean rate = 68.69 calls/second
                   min = 12.28 milliseconds
                   max = 29.65 milliseconds
                  mean = 14.53 milliseconds
                stddev = 2.30 milliseconds
                median = 14.17 milliseconds
                  95% <= 16.79 milliseconds
                  99% <= 26.81 milliseconds



CrimesInvestigatedBy STANDARD_OUT
    9/2/23, 12:04:31 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimesInvestigatedBy.crimes investigated by officer Larive - no indexes
                 count = 1000
             mean rate = 1424.77 calls/second
                   min = 0.50 milliseconds
                   max = 5.68 milliseconds
                  mean = 0.69 milliseconds
                stddev = 0.35 milliseconds
                median = 0.62 milliseconds
                  95% <= 1.00 milliseconds
                  99% <= 1.22 milliseconds



CrimesNewParticularAddress STANDARD_OUT
    9/2/23, 12:04:41 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    CrimesNewParticularAddress.crimes near to particular address
                 count = 100
             mean rate = 9.36 calls/second
                   min = 97.50 milliseconds
                   max = 138.02 milliseconds
                  mean = 106.66 milliseconds
                stddev = 7.17 milliseconds
                median = 104.80 milliseconds
                  95% <= 119.71 milliseconds
                  99% <= 135.16 milliseconds



PeopleRelatedWithDrugCrimes STANDARD_OUT
    9/2/23, 12:04:41 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    PeopleRelatedWithDrugCrimes.other related people associated with drugs crimes
                 count = 30
             mean rate = 317.44 calls/second
                   min = 2.36 milliseconds
                   max = 5.81 milliseconds
                  mean = 3.10 milliseconds
                stddev = 0.74 milliseconds
                median = 2.88 milliseconds
                  95% <= 4.90 milliseconds
                  99% <= 5.81 milliseconds



PersonKnowSubgraphAlgorithms STANDARD_OUT
    9/2/23, 12:04:43 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    PersonKnowSubgraphAlgorithms.betweenness centrality
                 count = 200
             mean rate = 115.18 calls/second
                   min = 7.22 milliseconds
                   max = 18.43 milliseconds
                  mean = 8.63 milliseconds
                stddev = 1.02 milliseconds
                median = 8.60 milliseconds
                  95% <= 9.67 milliseconds
                  99% <= 13.18 milliseconds



ShortestPathPersonRelatedToCrime STANDARD_OUT
    9/2/23, 12:04:43 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    ShortestPathPersonRelatedToCrime.shortest path between persons related to crimes
                 count = 100
             mean rate = 431.93 calls/second
                   min = 1.80 milliseconds
                   max = 6.74 milliseconds
                  mean = 2.29 milliseconds
                stddev = 0.70 milliseconds
                median = 2.05 milliseconds
                  95% <= 3.25 milliseconds
                  99% <= 5.91 milliseconds



Top5VulnerablePeople STANDARD_OUT
    9/2/23, 12:04:43 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    Top5VulnerablePeople.get top 5 vulnerable people
                 count = 100
             mean rate = 820.64 calls/second
                   min = 1.03 milliseconds
                   max = 3.49 milliseconds
                  mean = 1.20 milliseconds
                stddev = 0.30 milliseconds
                median = 1.14 milliseconds
                  95% <= 1.42 milliseconds
                  99% <= 2.68 milliseconds



TopLocationsForCrimes STANDARD_OUT
    9/2/23, 12:04:48 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    TopLocationsForCrimes.get top locations for crimes
                 count = 100
             mean rate = 21.01 calls/second
                   min = 41.37 milliseconds
                   max = 82.12 milliseconds
                  mean = 47.54 milliseconds
                stddev = 5.83 milliseconds
                median = 46.23 milliseconds
                  95% <= 55.79 milliseconds
                  99% <= 82.12 milliseconds



Gradle Test Executor 8 finished executing tests.

> Task :test
Finished generating test XML results (0.011 secs) into: /home/gangel/Personal/sources/graphomance/build/test-results/test
Generating HTML test report...
Finished generating test html results (0.003 secs) into: /home/gangel/Personal/sources/graphomance/build/reports/tests/test
Watching 55 directories to track changes
Watching 60 directories to track changes
Watching 61 directories to track changes

BUILD SUCCESSFUL in 51s
3 actionable tasks: 1 executed, 2 up-to-date
