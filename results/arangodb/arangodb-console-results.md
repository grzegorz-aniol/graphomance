NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 1:20:04 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 429.59 calls/second
                   min = 0.40 milliseconds
                   max = 45.65 milliseconds
                  mean = 1.14 milliseconds
                stddev = 2.91 milliseconds
                median = 0.79 milliseconds
                  95% <= 1.84 milliseconds
                  99% <= 2.74 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 2509.23 calls/second
                   min = 0.24 milliseconds
                   max = 2.82 milliseconds
                  mean = 0.38 milliseconds
                stddev = 0.12 milliseconds
                median = 0.37 milliseconds
                  95% <= 0.51 milliseconds
                  99% <= 0.63 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 881.76 calls/second
                   min = 0.38 milliseconds
                   max = 48.13 milliseconds
                  mean = 0.72 milliseconds
                stddev = 2.75 milliseconds
                median = 0.51 milliseconds
                  95% <= 0.78 milliseconds
                  99% <= 1.62 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 1:20:06 PM ============================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 645.38 calls/second
                   min = 0.26 milliseconds
                   max = 44.48 milliseconds
                  mean = 0.43 milliseconds
                stddev = 1.40 milliseconds
                median = 0.37 milliseconds
                  95% <= 0.48 milliseconds
                  99% <= 0.56 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 1882.81 calls/second
                   min = 0.19 milliseconds
                   max = 1.55 milliseconds
                  mean = 0.27 milliseconds
                stddev = 0.06 milliseconds
                median = 0.27 milliseconds
                  95% <= 0.36 milliseconds
                  99% <= 0.44 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 894.65 calls/second
                   min = 0.24 milliseconds
                   max = 43.83 milliseconds
                  mean = 0.58 milliseconds
                stddev = 2.69 milliseconds
                median = 0.39 milliseconds
                  95% <= 0.51 milliseconds
                  99% <= 0.91 milliseconds



FirstPartyFraud > Identify clients sharing PII(Session, QueryTimer) SKIPPED

FirstPartyFraud > Identify groups of clients sharing PII - fraud rings(Session, QueryTimer) SKIPPED

FirstPartyFraud > pairwise JACARD similarity scores(Session, QueryTimer) SKIPPED

ConnectionsBetweenVulnerablePersons > shortest connections between vulnerable persons(Session, QueryTimer) SKIPPED

CrimeTotals > count total number of crimes by type(Session, QueryTimer) SKIPPED

CrimesInvestigatedBy > crimes investigated by officer Larive - no indexes(Session, QueryTimer) SKIPPED

CrimesNewParticularAddress > crimes near to particular address(Session, QueryTimer) SKIPPED

PeopleRelatedWithDrugCrimes > other related people associated with drugs crimes(Session, QueryTimer) SKIPPED

PersonKnowSubgraphAlgorithms > betweenness centrality(Session, QueryTimer) SKIPPED

ShortestPathPersonRelatedToCrime > shortest path between persons related to crimes(Session, QueryTimer) SKIPPED

Top5VulnerablePeople > get top 5 vulnerable people(Session, QueryTimer) SKIPPED

TopLocationsForCrimes > get top locations for crimes(Session, QueryTimer) SKIPPED

Gradle Test Executor 28 finished executing tests.
