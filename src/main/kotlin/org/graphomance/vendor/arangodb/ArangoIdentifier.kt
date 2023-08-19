package org.graphomance.vendor.arangodb

import java.util.Objects
import org.graphomance.api.NodeIdentifier
import org.graphomance.api.RelationIdentifier

class ArangoIdentifier(
	val key: String,
	val id: String
) : NodeIdentifier, RelationIdentifier {

	companion object {
		fun getId(nodeId: NodeIdentifier): String {
			val idObj: ArangoIdentifier = Objects.requireNonNull(nodeId as ArangoIdentifier)
			return idObj.id
		}

		fun getKey(nodeId: NodeIdentifier): String {
			val idObj: ArangoIdentifier = Objects.requireNonNull(nodeId as ArangoIdentifier)
			return idObj.key
		}

		fun getId(edgeId: RelationIdentifier): String {
			val idObj: ArangoIdentifier = Objects.requireNonNull(edgeId as ArangoIdentifier)
			return idObj.id
		}

		fun getKey(edgeId: RelationIdentifier): String {
			val idObj: ArangoIdentifier = Objects.requireNonNull(edgeId as ArangoIdentifier)
			return idObj.key
		}
	}
}