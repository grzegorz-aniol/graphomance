package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringUniqueHashIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringUniqueHashIndex() {
        super("Create vertex with string unique hash index", IndexType.HASH_UNIQUE, false, true);
    }
    
}
