package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.IndexType

class CreateSingleVertexStringUniqueIndex : CreateSingleVertexBase("Create node with string unique index",
																   IndexType.UNIQUE,
																   false,
																   true) 