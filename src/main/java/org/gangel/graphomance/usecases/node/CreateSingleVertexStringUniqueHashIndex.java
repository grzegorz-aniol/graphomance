package org.gangel.graphomance.usecases.node;

import org.gangel.graphomance.DbType;
import org.gangel.graphomance.IndexType;

public class CreateSingleVertexStringUniqueHashIndex extends CreateSingleVertexBase {

    public CreateSingleVertexStringUniqueHashIndex() {
        super("Create node with string unique hash index", IndexType.HASH_UNIQUE, false, true);
    }

    @Override
    public boolean skipFor(DbType dbType) {
        return (dbType == DbType.NEO4J);
    }
}
