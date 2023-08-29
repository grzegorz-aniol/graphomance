package org.graphomance.vendor.neo4j

import org.graphomance.api.NodeIdentifier
import org.graphomance.api.RelationshipIdentifier

class NeoIdentifier(
    val id: Long // TODO: change to element ID
) : NodeIdentifier, RelationshipIdentifier {
    companion object {
        fun toLong(id: NodeIdentifier) = (id as NeoIdentifier).id
        fun toLong(id: RelationshipIdentifier) = (id as NeoIdentifier).id
    }
}