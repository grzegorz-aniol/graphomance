package org.gangel.graphomance.neo4j

import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.RelationIdentifier

class NeoIdentifier(
	val id: Long // TODO: change to element ID
) : NodeIdentifier, RelationIdentifier