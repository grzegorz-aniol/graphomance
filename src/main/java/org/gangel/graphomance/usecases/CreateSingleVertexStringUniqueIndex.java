package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringUniqueIndex() {
        super("Create vertex with string unique index", IndexType.UNIQUE, false, true);
    }
    
}
