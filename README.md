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

