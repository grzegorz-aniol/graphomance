package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexStringUniqueIndex : CreateSingleVertexBase("Create node with string unique index",
																   IndexType.UNIQUE,
																   false,
																   true) 