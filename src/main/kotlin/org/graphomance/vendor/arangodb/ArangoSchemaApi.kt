package org.graphomance.vendor.arangodb

import com.arangodb.ArangoCollection
import com.arangodb.ArangoDatabase
import com.arangodb.entity.CollectionType
import com.arangodb.model.CollectionCreateOptions
import java.util.Optional
import org.graphomance.api.SchemaApi

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
		if (classExists(className)) {
			return
		}
		db.createCollection(className)
	}

	override fun createRelationType(typeName: String) {
		if (classExists(typeName)) {
			return
		}
		db.createCollection(typeName, CollectionCreateOptions().type(CollectionType.EDGES))
	}

	override fun dropClass(className: String) {
		if (classExists(className)) {
			Optional.ofNullable(db.collection(className))
				.ifPresent { c: ArangoCollection -> c.drop() }
		}
	}
}