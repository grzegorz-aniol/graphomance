package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.DbType
import org.gangel.graphomance.IndexType

class CreateSingleVertexLongUniqueHashIndex :
	CreateSingleVertexBase("Create node with long unique hash index",
						   IndexType.HASH_UNIQUE,
						   true,
						   false) {
	override fun skipFor(dbType: DbType): Boolean {
		return dbType == DbType.NEO4J
	}
}