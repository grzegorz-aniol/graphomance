package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexLongUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexLongUniqueIndex() {
        super("Create node with long unique index", IndexType.UNIQUE, true, false);
    }

}
