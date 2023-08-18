package org.gangel.graphomance.usecases

import org.gangel.graphomance.api.Session

class CreateBasicRelationTest : TestBase() {
	override fun setUpTest(session: Session) {}
	override fun createTestData(session: Session) {
		val schemaApi = session.schemaApi()
		schemaApi.createClass("Person")
		schemaApi.createRelationClass("FriendOf")
	}

	override fun performTest(session: Session) {
		val objectApi = session.objectApi()
		val nodeId1 = objectApi.createNode("Person", mapOf("name" to "John"))
		val nodeId2 = objectApi.createNode("Person", mapOf("name" to "Kate"))
		val friendOf = objectApi.createRelation("FriendOf", nodeId1, nodeId2)
	}

	override fun cleanUpData(session: Session) {
		val objectApi = session.objectApi()
		val schemaApi = session.schemaApi()
		objectApi.deleteAllRelations("FriendOf")
		objectApi.deleteAllNodes("Person")
		schemaApi.dropClass("Person")
		schemaApi.dropClass("FriendOf")
	}
}