NodeSimpleCrud STANDARD_ERROR
    SLF4J: No SLF4J providers were found.
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
    ERROR StatusLogger Log4j2 could not find a logging implementation. Please add log4j-core to the classpath. Using SimpleLogger to log to the console...

NodeSimpleCrud STANDARD_OUT
    9/30/23, 12:07:07 PM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.NodeSimpleCrud.creating node with properties
                 count = 1000
             mean rate = 526.03 calls/second
                   min = 0.32 milliseconds
                   max = 45.77 milliseconds
                  mean = 0.91 milliseconds
                stddev = 2.82 milliseconds
                median = 0.57 milliseconds
                  95% <= 1.94 milliseconds
                  99% <= 2.59 milliseconds
    crud.NodeSimpleCrud.deleting nodes
                 count = 1000
             mean rate = 3799.90 calls/second
                   min = 0.14 milliseconds
                   max = 1.87 milliseconds
                  mean = 0.25 milliseconds
                stddev = 0.10 milliseconds
                median = 0.23 milliseconds
                  95% <= 0.36 milliseconds
                  99% <= 0.52 milliseconds
    crud.NodeSimpleCrud.updating node
                 count = 1000
             mean rate = 1042.18 calls/second
                   min = 0.27 milliseconds
                   max = 43.44 milliseconds
                  mean = 0.68 milliseconds
                stddev = 2.71 milliseconds
                median = 0.43 milliseconds
                  95% <= 1.02 milliseconds
                  99% <= 1.82 milliseconds



RelationshipSimpleCrud STANDARD_OUT
    9/30/23, 12:07:09 PM ===========================================================

    -- Timers ----------------------------------------------------------------------
    crud.RelationshipSimpleCrud.creating relationship
                 count = 1000
             mean rate = 922.77 calls/second
                   min = 0.14 milliseconds
                   max = 42.96 milliseconds
                  mean = 0.29 milliseconds
                stddev = 1.36 milliseconds
                median = 0.22 milliseconds
                  95% <= 0.32 milliseconds
                  99% <= 0.82 milliseconds
    crud.RelationshipSimpleCrud.deleting relationships
                 count = 1000
             mean rate = 2853.57 calls/second
                   min = 0.14 milliseconds
                   max = 1.72 milliseconds
                  mean = 0.20 milliseconds
                stddev = 0.06 milliseconds
                median = 0.19 milliseconds
                  95% <= 0.26 milliseconds
                  99% <= 0.33 milliseconds
    crud.RelationshipSimpleCrud.update relationships
                 count = 1000
             mean rate = 1268.05 calls/second
                   min = 0.15 milliseconds
                   max = 43.43 milliseconds
                  mean = 0.43 milliseconds
                stddev = 2.65 milliseconds
                median = 0.23 milliseconds
                  95% <= 0.37 milliseconds
                  99% <= 0.96 milliseconds



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

Gradle Test Executor 15 finished executing tests.
