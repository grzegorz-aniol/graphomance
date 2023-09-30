# Graphomance


## Project's goal
Performance tests for graph databases (e.g. Neo4j, Memgraph, ArangoDB)
The goal for this project is to create independent set of tests for different graph db providers.

## Supported database
Graphomance supports following graph databases:
* Neo4j 5.x
* Memgraph 2.x 
* ArangoDB 3.11.x

## Datasets

Datasets used in performance tests:

### POLE

This is crime investigation dataset, containing street-level crime in Greater Manchester. Dataset is originally published by data.gov.uk and transformed into independent dataset by Neo4j (see: https://github.com/neo4j-graph-examples/pole) 

POLE stands for Person, Object, Location and Event. Dataset contains 61521 nodes and 104862 relationships.

Dataset is loaded to Neo4j and Memgraph automatically on `docker-compose up` stage.

### Fraud

This is a synthetic fraud detection dataset generated and published by Neo4j on github repository at https://github.com/neo4j-graph-examples/fraud-detection/blob/main/README.adoc
Queries used in test scenarios follow examples or they are inspired by [documentation page](https://github.com/neo4j-graph-examples/fraud-detection/blob/main/documentation/fraud-detection.adoc)

Fraud dataset contains 332973 nodes and 980857 relationships.

Dataset is loaded to Neo4j and Memgraph automatically on `docker-compose up` stage.


## Running all test

### Running docker compose
Rename `.env.local` file to `.env` and run `docker-compose up -d`. You may need to wait few minutes until all services are up and datasets data are loaded.

### Master script
Before running all tests, make sure the host OS is not busy. Verify with CPU utilization that all services are just on standby.
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

#### Recent versions tested
```
NEO4J_VERSION=5.10.0-enterprise
MEMGRAPH_VERSION=2.10.0
ARANGODB_VERSION=3.11.2
```

**Here you can find [MASTER RESULTS FILE](results/master-results.csv)**

You can find individual test results in following sub folders:
* [Neo4j results](results/neo4j/).
* [Memgraph results](results/memgraph/).
* [ArangoDB results](results/arangodb/).


### Graph database differences

#### Differences between Cypher implementation in Neo4j and Memgraph 

* Properties on edged are disabled by default. You have run Memgraph with explicit setting `--storage-properties-on-edges=true`
* POINT type is not supported in Memgraph, in which geolocation should be stored in the node with properties `lng` and `lat`
* LOAD CSV syntax is very similar, however different:
** file name following `WITH HEADER` (memgraph) vs opposite place, `WITH HEADERS` following file name  (Neo4j)
* Different syntax for creating indexes:
** Neo4j syntax: `create index for (n:Location) ON (n.address);`
** Memgraph syntax: `create index on :Location(address);`
