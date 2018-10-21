package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringIndex() {
        super("Create vertex with string index", IndexType.DEFAULT, false, true);
    }
    
}
