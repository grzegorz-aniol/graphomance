package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexCompoundUniqueIndex :
	CreateSingleVertexBase("Create node with compound unique index",
						   IndexType.UNIQUE,
						   true,
						   true) 