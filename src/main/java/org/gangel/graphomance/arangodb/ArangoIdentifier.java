package org.gangel.graphomance.arangodb;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Objects;

@Builder
public class ArangoIdentifier implements NodeIdentifier, RelationIdentifier {

    @Getter
    private String id;

    public static String getId(NodeIdentifier nodeId) {
        ArangoIdentifier idObj = Objects.requireNonNull((ArangoIdentifier) nodeId);
        return idObj.getId();
    }

}

