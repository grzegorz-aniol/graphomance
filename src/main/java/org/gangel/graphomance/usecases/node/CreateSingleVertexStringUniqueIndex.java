package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringUniqueIndex() {
        super("Create node with string unique index", IndexType.UNIQUE, false, true);
    }
    
}
