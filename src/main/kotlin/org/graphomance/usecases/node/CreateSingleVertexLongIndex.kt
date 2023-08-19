package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexLongIndex :
	CreateSingleVertexBase("Create node with long index", IndexType.DEFAULT, true, false)