package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.api.IndexType

class CreateSingleVertexLongUniqueIndex : CreateSingleVertexBase("Create node with long unique index",
																 IndexType.UNIQUE,
																 true,
																 false) 