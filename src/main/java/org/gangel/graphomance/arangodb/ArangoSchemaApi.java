package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.CollectionStatus;
import com.arangodb.entity.CollectionType;
import com.arangodb.model.CollectionCreateOptions;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.SchemaApi;

import java.util.Optional;

@Builder
public class ArangoSchemaApi implements SchemaApi {

    @Getter
    private ArangoDatabase db;

    @Override
    public long countObjects(String clsName) {
        return Optional.ofNullable(db.collection(clsName))
                .map(c->c.count().getCount().longValue())
                .orElse(0L);
    }

    @Override
    public boolean classExists(String clsName) {
        return db.collection(clsName).exists();
    }

    @Override
    public void createClass(String clsName) {
        db.createCollection(clsName);
    }

    @Override
    public void createNodeClass(String clsName) {
        db.createCollection(clsName);
    }

    @Override
    public void createRelationClass(String clsName) {
        db.createCollection(clsName, new CollectionCreateOptions().type(CollectionType.EDGES));
    }

    @Override
    public void createClass(String clsName, String extendsClass) {
        createClass(clsName);
    }

    @Override
    public void dropClass(String clsName) {
        if (classExists(clsName)) {
            Optional.ofNullable(db.collection(clsName)).ifPresent(c -> c.drop() );
        }
    }

    @Override
    public void createProperty(String clsName, String propName, Class type, boolean isMandatory) {
    }

    @Override
    public void createIndex(String indexName, String clsName, IndexType indexType, boolean isUnique, String... propNames) {
    }

    @Override
    public void dropAllIndexesOnClass(String clsName) {
    }
}
