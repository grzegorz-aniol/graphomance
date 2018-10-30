package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringIndex() {
        super("Create node with string index", IndexType.DEFAULT, false, true);
    }
    
}
