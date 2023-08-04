package org.gangel.graphomance.neo4j

import org.gangel.graphomance.SchemaApi
import java.time.Duration

internal class NeoSchemaApi(private val session: NeoSession) : SchemaApi {

	override fun countObjects(className: String): Long {
		return session.neo4jSession
				.run("match (a:`$className`) return count(a) as result")
				.single().get("result").asLong()
	}

	override fun classExists(className: String): Boolean {
		return countObjects(className) != 0L
	}

	override fun createClass(className: String) {
		// no schema in neo4j
	}

	override fun createRelationClass(className: String) {
		// no schema in neo4j
	}

	override fun dropClass(className: String) {
		// no schema in neo4j
	}
}