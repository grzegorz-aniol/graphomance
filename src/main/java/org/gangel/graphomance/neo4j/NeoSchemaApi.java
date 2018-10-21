package org.gangel.graphomance.neo4j;

import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.SchemaApi;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.ConstraintCreator;
import org.neo4j.graphdb.schema.IndexCreator;
import org.neo4j.graphdb.schema.IndexDefinition;

import java.util.concurrent.TimeUnit;

class NeoSchemaApi implements SchemaApi {

    private final GraphDatabaseService dbService;
    private final NeoSession session;

    NeoSchemaApi(NeoSession session) {
        this.session = session;
        this.dbService = session.getDbService();
    }


    @Override
    public boolean classExists(String clsName) {
        return false;
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
        createIndex(clsName, propNames);
        if (isUnique) {
            createUniqueConstains(clsName, propNames);
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
        try ( Transaction tx = this.dbService.beginTx() )
        {
            IndexCreator indexCreator = dbService.schema().indexFor(Label.label(clsName));

            for (String prop : propNames) {
                indexCreator = indexCreator.on(prop);
            }
            indexDefinition = indexCreator.create();

            dbService.schema().awaitIndexOnline( indexDefinition, 10, TimeUnit.SECONDS );
            tx.success();
        }

    }
}
