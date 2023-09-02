#!/bin/sh
set -e

echo Neo4j tests dry run
DB_TYPE=NEO4J URL=bolt://localhost:7687 gradle test -i --rerun

echo Neo4j tests run
rm ./build/*.csv
DB_TYPE=NEO4J URL=bolt://localhost:7687 gradle test -i --rerun | tee ./results/neo4j/neo4j-console-results.md
rm ./results/neo4j/*.csv
mv ./build/*.csv ./results/neo4j/

echo Memgraph tests dry run
DB_TYPE=MEMGRAPH URL=bolt://localhost:7688 gradle test -i --rerun

echo Memgraph tests run
rm ./build/*.csv
DB_TYPE=MEMGRAPH URL=bolt://localhost:7688 gradle test -i --rerun | tee ./results/memgraph/memgraph-console-results.md
rm ./results/memgraph/*.csv
mv ./build/*.csv ./results/memgraph/
