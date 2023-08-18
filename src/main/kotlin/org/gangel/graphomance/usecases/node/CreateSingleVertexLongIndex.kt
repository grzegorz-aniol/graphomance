package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.api.IndexType

class CreateSingleVertexLongIndex :
	CreateSingleVertexBase("Create node with long index", IndexType.DEFAULT, true, false)