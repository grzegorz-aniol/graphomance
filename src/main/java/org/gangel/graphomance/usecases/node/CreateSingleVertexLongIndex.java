package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexLongIndex extends CreateSingleVertexBase {

    public CreateSingleVertexLongIndex() {
        super("Create node with long index", IndexType.DEFAULT, true, false);
    }

}
