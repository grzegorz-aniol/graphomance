package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexLongUniqueIndex : CreateSingleVertexBase("Create node with long unique index",
																 IndexType.UNIQUE,
																 true,
																 false) 