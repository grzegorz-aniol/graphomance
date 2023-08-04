package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.IndexType

class CreateSingleVertexCompoundUniqueIndex :
	CreateSingleVertexBase("Create node with compound unique index",
						   IndexType.UNIQUE,
						   true,
						   true) 