package org.gangel.graphomance.neo4j;

import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.RelationIdentifier;
import org.neo4j.graphdb.*;

import java.util.Map;

public class NeoObjectApi implements ObjectApi {

    private final GraphDatabaseService dbService;
    private Transaction transaction;

    NeoObjectApi(NeoSession session) {
        this.dbService = session.getDbService();
    }

    @Override
    public void startTransaction() {
        if (transaction != null) {
            throw new RuntimeException("Transaction is already started!");
        }
        transaction = dbService.beginTx();
    }

    @Override
    public void commit() {
        if (transaction == null) {
            throw new RuntimeException("Transaction hasn't been started!");
        }
        transaction.success();
        transaction = null;
    }

    @Override
    public void rollback() {
        if (transaction == null) {
            throw new RuntimeException("Transaction hasn't been started!");
        }
        transaction.failure();
        transaction = null;

    }

    @Override
    public NodeIdentifier createNode(String clsName, Map<String, Object> properties) {
        try(Result rs = dbService.execute(String.format("create (n:%s $map) return ID(n)", clsName), Map.of("map", properties))) {
            if (!rs.hasNext()) {
                throw new RuntimeException("Can't read ID of created node");
            }
            Long id = (Long)rs.next().get(0);
            return NeoIdentifier.builder().id(id).build();
        }
    }

    @Override
    public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode, Map<String, Object> properties) {
        return null;
    }

    @Override
    public void deleteAllNodes(String clsName) {
        try(Result rs = dbService.execute(String.format("match (n:%s) delete n", clsName))) {
        }
    }

    @Override
    public void deleteAllRelations(String clsName) {
        try(Result rs = dbService.execute(String.format("match (a)-[e:%s]->(b) delete e", clsName))) {
        }
    }
}
