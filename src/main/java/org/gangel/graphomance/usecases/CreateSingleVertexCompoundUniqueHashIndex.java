package org.gangel.graphomance.usecases;

import org.gangel.graphomance.DbType;
import org.gangel.graphomance.IndexType;

public class CreateSingleVertexCompoundUniqueHashIndex extends CreateSingleVertexBase {

    public CreateSingleVertexCompoundUniqueHashIndex() {
        super("Create vertex with compound unique hash index", IndexType.HASH_UNIQUE, true, true);
    }

    @Override
    public boolean skipFor(DbType dbType) {
        return (dbType == DbType.NEO4J);
    }
}
