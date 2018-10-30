package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.IndexType;

public class CreateSingleVertexCompoundUniqueIndex extends CreateSingleVertexBase {

    public CreateSingleVertexCompoundUniqueIndex() {
        super("Create node with compound unique index", IndexType.UNIQUE, true, true);
    }

}
