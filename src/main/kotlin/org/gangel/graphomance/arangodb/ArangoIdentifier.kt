package org.gangel.graphomance.arangodb

import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.RelationIdentifier
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