package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexLongIndex extends CreateSingleVertexBase {

    public CreateSingleVertexLongIndex() {
        super("Create vertex with long index", IndexType.DEFAULT, true, false);
    }

}
