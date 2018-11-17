package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Map;

@Builder
public class ArangoObjectApi implements ObjectApi {

    @Getter
    private ArangoDatabase db;

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
        return null;
    }

    @Override
    public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode, Map<String, Object> properties) {
        return null;
    }

    @Override
    public void deleteAllNodes(String clsName) {

    }

    @Override
    public void deleteAllRelations(String clsName) {

    }
}
