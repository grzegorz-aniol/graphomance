package org.gangel.graphomance.usecases;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexCompoundUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexCompoundUniqueIndex() {
        super("Create vertex with compound unique index", IndexType.UNIQUE, true, true);
    }

}
