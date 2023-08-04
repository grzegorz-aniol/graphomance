package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.DbType
import org.gangel.graphomance.IndexType

class CreateSingleVertexStringUniqueHashIndex :
	CreateSingleVertexBase("Create node with string unique hash index",
						   IndexType.HASH_UNIQUE,
						   false,
						   true) {
	override fun skipFor(dbType: DbType): Boolean {
		return dbType == DbType.NEO4J
	}
}