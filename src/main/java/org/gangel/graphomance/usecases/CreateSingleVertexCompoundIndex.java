package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexCompoundIndex extends CreateSingleVertexBase {

    public CreateSingleVertexCompoundIndex() {
        super("Create vertex with compound index", IndexType.DEFAULT, true, true);
    }

}
