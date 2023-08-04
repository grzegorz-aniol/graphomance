package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.DbType
import org.gangel.graphomance.IndexType

class CreateSingleVertexCompoundUniqueHashIndex :
	CreateSingleVertexBase("Create node with compound unique hash index",
						   IndexType.HASH_UNIQUE,
						   true,
						   true) {
	override fun skipFor(dbType: DbType): Boolean {
		return dbType == DbType.NEO4J
	}
}