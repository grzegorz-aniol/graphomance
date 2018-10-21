package org.gangel.graphomance.neo4j;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;

@Builder
public class NeoIdentifier implements NodeIdentifier, RelationIdentifier {

    @Getter
    long id;
}
