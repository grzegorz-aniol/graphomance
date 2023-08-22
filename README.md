# Graphomance
Performance tests for graph databases (e.g. OrientDB, Neo4j, ArangoDB)

# Motivation
The goal for this project is to create independent set of tests for different graph db providers.

# Supported database
Graphomance works with following graph databases:
* Neo4j 5.x
* Memgraph 2.10.x 
* ArangoDB 3.11.x

# Datasets

Datasets used in performance tests:

## POLE

This is crime investigation dataset, containing street-level crime in Greater Manchester. Dataset is originally published by data.gov.uk and transformed into independent dataset by Neo4j (see: https://github.com/neo4j-graph-examples/pole) 

POLE stands for Person, Object, Location and Event. Dataset contains 62177 nodes and 105840 relationships.

## Graph database differences

### Differences between Cypher implementation in Neo4j and Memgraph 

* Properties on edged are disabled by default. You have run Memgraph with explicit setting `--storage-properties-on-edges=true`
* POINT type is not supported in Memgraph, in which geolocation should be stored in the node with properties `lng` and `lat`
* LOAD CSV syntax is very similar, however different:
** file name following `WITH HEADER` (memgraph) vs opposite place, `WITH HEADERS` following file name  (Neo4j)
