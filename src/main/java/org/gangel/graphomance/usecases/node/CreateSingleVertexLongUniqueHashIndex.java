package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.DbType;
import org.gangel.graphomance.IndexType;

public class CreateSingleVertexLongUniqueHashIndex extends CreateSingleVertexBase {

    public CreateSingleVertexLongUniqueHashIndex() {
        super("Create node with long unique hash index", IndexType.HASH_UNIQUE, true, false);
    }

    @Override
    public boolean skipFor(DbType dbType) {
        return (dbType == DbType.NEO4J);
    }
}
