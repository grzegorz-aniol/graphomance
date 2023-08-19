package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexCompoundIndex :
	CreateSingleVertexBase("Create node with compound index", IndexType.DEFAULT, true, true)