package org.gangel.graphomance.vendor.arangodb

import org.gangel.graphomance.api.NodeIdentifier
import org.gangel.graphomance.api.RelationIdentifier
import java.util.Objects

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