package org.gangel.graphomance.neo4j;

import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.SchemaApi;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class NeoSchemaApi implements SchemaApi {

    private final GraphDatabaseService dbService;
    private final NeoSession session;
    private final Duration AWAIT_INDEX_ONLINE = Duration.ofSeconds(30);

    NeoSchemaApi(NeoSession session) {
        this.session = session;
        this.dbService = session.getDbService();
    }

    @Override
    public long countObjects(String clsName) {
        try(Result rs = dbService.execute(String.format("match (a:%s) return count(a)", clsName))) {
            if (!rs.hasNext()) {
                throw new RuntimeException("Can't read number of nodes with the label");
            }
            Map<String, Object> map = rs.next();
            Long id = (long)map.get("count(a)");
            return id;
        }
    }

    @Override
    public boolean classExists(String clsName) {
        return countObjects(clsName) != 0;
    }

    @Override
    public void createClass(String clsName) {
        // no schema in neo4j
    }

    @Override
    public void dropClass(String clsName) {
        // no schema in neo4j
    }

    @Override
    public void createProperty(String clsName, String propName, Class type, boolean isMandatory) {
        // no schema in neo4j
    }

    @Override
    public void createIndex(String indexName, String clsName, IndexType indexType, boolean isUnique, String... propNames) {
        if (isUnique) {
            createUniqueConstains(clsName, propNames);
        } else {
            createIndex(clsName, propNames);
        }
    }

    @Override
    public void dropAllIndexesOnClass(final String clsName) {
        Label label = Label.label(clsName);
        try ( Transaction tx = this.dbService.beginTx() )
        {
            {
                Iterator<IndexDefinition> iter = dbService.schema().getIndexes(label).iterator();
                while( iter.hasNext() ) {
                    IndexDefinition index = iter.next();
                    if (!index.isConstraintIndex()) {
                        index.drop();
                    }
                }
            }

            {
                Iterator<ConstraintDefinition> iter2 = dbService.schema().getConstraints(label).iterator();
                while (iter2.hasNext()) {
                    ConstraintDefinition c = iter2.next();
                    if (c.isConstraintType(ConstraintType.UNIQUENESS)) {
                        c.drop();
                    }
                }
            }

            tx.success();
        }
    }

    public void createUniqueConstains(String clsName, String... propNames) {
        try ( Transaction tx = this.dbService.beginTx() )
        {
            ConstraintCreator constraintCreator = this.dbService.schema()
                    .constraintFor(Label.label(clsName));

            for (String prop : propNames) {
                constraintCreator = constraintCreator.assertPropertyIsUnique(prop);
            }

            constraintCreator.create();
            tx.success();
        }
    }

    public void createIndex(String clsName, String... propNames) {

        IndexDefinition indexDefinition;
        try ( Transaction tx = this.dbService.beginTx() ) {
            IndexCreator indexCreator = dbService.schema().indexFor(Label.label(clsName));

            for (String prop : propNames) {
                indexCreator = indexCreator.on(prop);
            }
            indexDefinition = indexCreator.create();

            tx.success();
        }

        try ( Transaction tx = this.dbService.beginTx() ) {
            dbService.schema().awaitIndexOnline(indexDefinition, AWAIT_INDEX_ONLINE.toSeconds(), TimeUnit.SECONDS);
        }
    }
}
