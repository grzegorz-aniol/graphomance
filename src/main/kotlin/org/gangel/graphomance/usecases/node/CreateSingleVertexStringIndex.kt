package org.gangel.graphomance.usecases.node

import org.gangel.graphomance.api.IndexType

class CreateSingleVertexStringIndex :
	CreateSingleVertexBase("Create node with string index", IndexType.DEFAULT, false, true)