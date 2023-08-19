package org.graphomance.vendor.neo4j

import org.graphomance.api.NodeIdentifier
import org.graphomance.api.RelationIdentifier

class NeoIdentifier(
	val id: Long // TODO: change to element ID
) : NodeIdentifier, RelationIdentifier {
	companion object {
		fun toLong(id: NodeIdentifier) = (id as NeoIdentifier).id
		fun toLong(id: RelationIdentifier) = (id as NeoIdentifier).id
	}
}