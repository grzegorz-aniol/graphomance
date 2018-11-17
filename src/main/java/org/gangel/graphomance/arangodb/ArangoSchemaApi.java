package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
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
        return Optional.ofNullable(db.collection(clsName)).;
    }

    @Override
    public boolean classExists(String clsName) {
        return false;
    }

    @Override
    public void createClass(String clsName) {

    }

    @Override
    public void createNodeClass(String clsName) {

    }

    @Override
    public void createRelationClass(String clsName) {

    }

    @Override
    public void createClass(String clsName, String extendsClass) {

    }

    @Override
    public void dropClass(String clsName) {

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
