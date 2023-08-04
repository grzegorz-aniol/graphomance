package org.gangel.graphomance.vendor.neo4j

import org.gangel.graphomance.api.NodeIdentifier
import org.gangel.graphomance.api.RelationIdentifier

class NeoIdentifier(
	val id: Long // TODO: change to element ID
) : NodeIdentifier, RelationIdentifier {
	companion object {
		fun toLong(id: NodeIdentifier) = (id as NeoIdentifier).id
		fun toLong(id: RelationIdentifier) = (id as NeoIdentifier).id
	}
}