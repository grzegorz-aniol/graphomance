package org.gangel.graphomance.vendor.neo4j

import org.gangel.graphomance.api.NodeIdentifier
import org.gangel.graphomance.api.RelationIdentifier

class NeoIdentifier(
	val id: Long // TODO: change to element ID
) : NodeIdentifier, RelationIdentifier