package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.model.CollectionsReadOptions;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Map;
import java.util.Optional;

@Builder
public class ArangoObjectApi implements ObjectApi {

    @Getter
    private ArangoDatabase db;

    private boolean classExists(String clsName) {
//        return db.getCollections().stream().filter(c -> c.getName().equals(clsName)).count() != 0;
        return db.collection(clsName).exists();
    }

    @Override
    public void startTransaction() {
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }

    @Override
    public NodeIdentifier createNode(String clsName, Map<String, Object> properties) {
        BaseDocument doc = new BaseDocument();
        if (properties!=null) {
            doc.setProperties(properties);
        }
        return ArangoIdentifier.builder().id( db.collection(clsName).insertDocument(doc).getId() ).build();
    }

    @Override
    public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode, Map<String, Object> properties) {
        BaseEdgeDocument doc = new BaseEdgeDocument();
        doc.setFrom(ArangoIdentifier.getId(fromNode));
        doc.setTo(ArangoIdentifier.getId(toNode));
        if (properties != null) {
            doc.setProperties(properties);
        }
        return ArangoIdentifier.builder()
                .id(db.collection(className).insertDocument(doc).getId())
                .build();
    }

    @Override
    public void deleteAllNodes(String clsName) {
        if (classExists(clsName)) {
            Optional.ofNullable(db.collection(clsName))
                    .ifPresent(c -> c.drop());
        }
    }

    @Override
    public void deleteAllRelations(String clsName) {
        if (classExists(clsName)) {
            Optional.ofNullable(db.collection(clsName))
                    .ifPresent(c -> c.drop());
        }
    }
}
