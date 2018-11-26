package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.model.CollectionsReadOptions;
import com.arangodb.util.MapBuilder;
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
    public Object getNode(final String clsName, final NodeIdentifier nodeId) {
        BaseDocument doc = db.collection(clsName).getDocument(ArangoIdentifier.getKey(nodeId), BaseDocument.class);
        return doc;
    }

    @Override
    public long shortestPath(NodeIdentifier start, NodeIdentifier end) {
        final String edgeClsName = "RoadTo";
        final String TEMPL  =
            String.format("RETURN LENGTH(\n" +
                "  FOR v IN ANY\n" +
                "    SHORTEST_PATH @a TO @b %s\n" +
                "    RETURN v\n" +
                ")", edgeClsName);

        Map<String, Object> bindVars = new MapBuilder()
            .put("a", ArangoIdentifier.getId(start))
            .put("b", ArangoIdentifier.getId(end))
            .get();

        ArangoCursor<Long> cursor = db.query(TEMPL, bindVars, null, Long.class);

        if (cursor.hasNext()) {
            return cursor.next();
        }

        return -1;
    }

    @Override
    public NodeIdentifier createNode(String clsName, Map<String, Object> properties) {
        BaseDocument doc = new BaseDocument();
        if (properties!=null) {
            doc.setProperties(properties);
        }
        DocumentCreateEntity<BaseDocument> response = db.collection(clsName).insertDocument(doc);
        return ArangoIdentifier.builder()
            .id(response.getId())
            .key(response.getKey())
            .build();
    }

    @Override
    public void updateNode(String clsName, NodeIdentifier nodeId, Map<String, Object> properties) {
        BaseDocument doc = new BaseDocument();
        if (properties!=null) {
            doc.setProperties(properties);
        }
        db.collection(clsName).updateDocument(ArangoIdentifier.getKey(nodeId), doc);
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
                .key(db.collection(className).insertDocument(doc).getKey())
                .build();
    }

    @Override
    public void updateRelation(String clsName, RelationIdentifier edgeId, Map<String, Object> properties) {
        BaseDocument doc = new BaseDocument();
        if (properties!=null) {
            doc.setProperties(properties);
        }
        db.collection(clsName).updateDocument(ArangoIdentifier.getKey(edgeId), doc);
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
