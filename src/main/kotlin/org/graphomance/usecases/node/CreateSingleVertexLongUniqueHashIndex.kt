package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexLongUniqueHashIndex :
	CreateSingleVertexBase("Create node with long unique hash index",
						   IndexType.HASH_UNIQUE,
						   true,
						   false) {
	override fun skipFor(dbType: org.graphomance.api.DbType): Boolean {
		return dbType == org.graphomance.api.DbType.NEO4J
	}
}