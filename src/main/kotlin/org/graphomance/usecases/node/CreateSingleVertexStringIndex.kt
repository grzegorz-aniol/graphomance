package org.graphomance.usecases.node

import org.graphomance.api.IndexType

class CreateSingleVertexStringIndex :
	CreateSingleVertexBase("Create node with string index", IndexType.DEFAULT, false, true)