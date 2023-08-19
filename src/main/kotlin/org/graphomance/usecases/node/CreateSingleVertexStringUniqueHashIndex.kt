package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexStringUniqueHashIndex :
	CreateSingleVertexBase("Create node with string unique hash index",
						   IndexType.HASH_UNIQUE,
						   false,
						   true) {
	override fun skipFor(dbType: org.graphomance.api.DbType): Boolean {
		return dbType == org.graphomance.api.DbType.NEO4J
	}
}