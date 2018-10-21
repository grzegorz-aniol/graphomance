package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexLongUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexLongUniqueIndex() {
        super("Create vertex with long unique index", IndexType.UNIQUE, true, false);
    }

}
