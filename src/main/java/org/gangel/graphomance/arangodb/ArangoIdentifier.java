package org.gangel.graphomance.arangodb;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Objects;

@Builder
public class ArangoIdentifier implements NodeIdentifier, RelationIdentifier {

    @Getter
    private String key;

    @Getter
    private String id;

    public static String getId(NodeIdentifier nodeId) {
        ArangoIdentifier idObj = Objects.requireNonNull((ArangoIdentifier) nodeId);
        return idObj.getId();
    }

    public static String getKey(NodeIdentifier nodeId) {
        ArangoIdentifier idObj = Objects.requireNonNull((ArangoIdentifier) nodeId);
        return idObj.getKey();
    }

    public static String getId(RelationIdentifier edgeId) {
        ArangoIdentifier idObj = Objects.requireNonNull((ArangoIdentifier) edgeId);
        return idObj.getId();
    }

    public static String getKey(RelationIdentifier edgeId) {
        ArangoIdentifier idObj = Objects.requireNonNull((ArangoIdentifier) edgeId);
        return idObj.getKey();
    }

}

