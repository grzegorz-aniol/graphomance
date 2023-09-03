# Graphomance


## Project's goal
Performance tests for graph databases (e.g. Neo4j, Memgraph, ArangoDB)
The goal for this project is to create independent set of tests for different graph db providers.

## Supported database
Graphomance supports following graph databases:
* Neo4j 5.x
* Memgraph 2.10.x 
* ArangoDB 3.11.x

## Creating default database for ArangoDB

> docker exec graphomance-arangodb-1 arangosh --log.level debug --server.password password --server.endpoint tcp://localhost:8529 --javascript.execute-string "db._createDatabase('test');"

## Datasets

Datasets used in performance tests:

### POLE

This is crime investigation dataset, containing street-level crime in Greater Manchester. Dataset is originally published by data.gov.uk and transformed into independent dataset by Neo4j (see: https://github.com/neo4j-graph-examples/pole) 

POLE stands for Person, Object, Location and Event. Dataset contains 61521 nodes and 104862 relationships.

#### Loading POLE dataset to Memgraph

> docker exec -it graphomance-memgraph-1 /bin/bash -c 'mgconsole < /datasets/pole/memgraph-load.cypher'

#### Loading POLE dataset to Neo4j

> docker exec -it graphomance-neo4j5-1 bin/cypher-shell -u neo4j -p password -d neo4j 'create database pole;'
> docker exec -it graphomance-neo4j5-1 bin/cypher-shell -u neo4j -p password -d pole -f /import/datasets/pole/neo4j-load.cypher

## Running all test

### Master script
Assuming all graph database services are up and running, you can run all tests with just one script command:
> ./run.sh

### Individual steps

However, you can also run group of tests individually, with gradle task. However, some environment variable are required then. 

Following command runs all available tests:
> DB_TYPE=<DB_TYPE> URL=bolt://localhost:<BOLT_PORT> gradle test -i --rerun

Replace DB_TYPE with one of possible db providers: NEO4J, MEMGRAPH

For example, to run all tests for Neo4j use this command:
> DB_TYPE=NEO4J URL=bolt://localhost:7687 gradle test -i --rerun

Similarly, this command run all tests for Memgraph (please note a non-standard bolt port)
> DB_TYPE=MEMGRAPH URL=bolt://localhost:7688 gradle test -i --rerun

Finally, the command to run all ArangoDB tests:
> DB_NAME=test DB_TYPE=ARANGODB URL=localhost:8529 gradle test -i --rerun

The last step of the process combines all individual test results into master CSV file. File `results/master-results.csv` contains all test results merged per each database provider. It can be used to generate chart. Master file can be regenerated after completing all results with the following command:
> gradle combineResults

### Test results

**Here you can find [MASTER RESULTS FILE](results/master-results.csv)**

You can find individual test results in sub folder:
* [Neo4j results](results/neo4j/).
* [Memgraph results](results/memgraph/).


### Graph database differences

#### Differences between Cypher implementation in Neo4j and Memgraph 

* Properties on edged are disabled by default. You have run Memgraph with explicit setting `--storage-properties-on-edges=true`
* POINT type is not supported in Memgraph, in which geolocation should be stored in the node with properties `lng` and `lat`
* LOAD CSV syntax is very similar, however different:
** file name following `WITH HEADER` (memgraph) vs opposite place, `WITH HEADERS` following file name  (Neo4j)
* Different syntax for creating indexes:
** Neo4j syntax: `create index for (n:Location) ON (n.address);`
** Memgraph syntax: `create index on :Location(address);`
