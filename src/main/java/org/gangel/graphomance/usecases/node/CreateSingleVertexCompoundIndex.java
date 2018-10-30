package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexCompoundIndex extends CreateSingleVertexBase {

    public CreateSingleVertexCompoundIndex() {
        super("Create node with compound index", IndexType.DEFAULT, true, true);
    }

}
