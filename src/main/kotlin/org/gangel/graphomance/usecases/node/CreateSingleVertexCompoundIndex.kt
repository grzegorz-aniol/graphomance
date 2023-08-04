package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.api.IndexType

class CreateSingleVertexCompoundIndex :
	CreateSingleVertexBase("Create node with compound index", IndexType.DEFAULT, true, true)