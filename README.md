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

POLE stands for Person, Object, Location and Event. Dataset contains 61521 nodes and 104862 relationships.

### Loading POLE dataset to Memgraph

> docker exec -it graphomance-memgraph-1 /bin/bash -c 'mgconsole < /datasets/pole/memgraph-load.cypher'

### Loading POLE dataset to Neo4j

> docker exec -it graphomance-neo4j5-1 bin/cypher-shell -u neo4j -p password -d neo4j 'create database pole;'
> docker exec -it graphomance-neo4j5-1 bin/cypher-shell -u neo4j -p password -d pole -f /import/datasets/pole/neo4j-load.cypher

## Running test

Following command runs all available tests:
> DB_TYPE=<DB_TYPE> URL=bolt://localhost:7687 gradle test -i --rerun

Replace DB_TYPE with one of possible db providers: NEO4J, MEMGRAPH

## Test results

### Neo4j
You can find recent Neo4j result ins sub folder [Neo4j results](results/neo4j/).

### Memgraph
You can find recent Memgraph results in sub folder [Memgraph results](results/memgraph/).


## Graph database differences

### Differences between Cypher implementation in Neo4j and Memgraph 

* Properties on edged are disabled by default. You have run Memgraph with explicit setting `--storage-properties-on-edges=true`
* POINT type is not supported in Memgraph, in which geolocation should be stored in the node with properties `lng` and `lat`
* LOAD CSV syntax is very similar, however different:
** file name following `WITH HEADER` (memgraph) vs opposite place, `WITH HEADERS` following file name  (Neo4j)
* Different syntax for creating indexes:
** Neo4j syntax: `create index for (n:Location) ON (n.address);`
** Memgraph syntax: `create index on :Location(address);`
