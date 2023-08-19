package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexCompoundUniqueHashIndex :
	CreateSingleVertexBase("Create node with compound unique hash index",
						   IndexType.HASH_UNIQUE,
						   true,
						   true) {
	override fun skipFor(dbType: org.graphomance.api.DbType): Boolean {
		return dbType == org.graphomance.api.DbType.NEO4J
	}
}