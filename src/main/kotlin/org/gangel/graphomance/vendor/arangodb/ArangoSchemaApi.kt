package org.gangel.graphomance.vendor.arangodb

import com.arangodb.ArangoCollection
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionType
import com.arangodb.model.CollectionCreateOptions
import org.gangel.graphomance.api.SchemaApi
import java.util.Optional

class ArangoSchemaApi(
	private val db: ArangoDatabase
) : SchemaApi {
	
	override fun countObjects(className: String): Long {
		return Optional.ofNullable(db.collection(className))
			.map { c: ArangoCollection ->
				c.count()
					.count
					.toLong()
			}
			.orElse(0L)
	}

	override fun classExists(className: String): Boolean {
		return db.collection(className)
			.exists()
	}

	override fun createClass(className: String) {
		db.createCollection(className)
	}

	override fun createRelationClass(className: String) {
		db.createCollection(className, CollectionCreateOptions().type(CollectionType.EDGES))
	}

	override fun dropClass(className: String) {
		if (classExists(className)) {
			Optional.ofNullable(db.collection(className))
				.ifPresent { c: ArangoCollection -> c.drop() }
		}
	}
}