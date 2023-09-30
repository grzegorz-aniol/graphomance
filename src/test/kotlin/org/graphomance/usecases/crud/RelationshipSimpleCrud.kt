package org.graphomance.usecases.crud

import com.github.javafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.NodeIdentifier
import org.graphomance.api.RelationshipIdentifier
import org.graphomance.api.Session
import org.graphomance.engine.GraphomanceTest
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@GraphomanceTest(dbTargets = [DbType.NEO4J, DbType.MEMGRAPH, DbType.ARANGODB])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class RelationshipSimpleCrud {

    private companion object {
        val classNames = arrayListOf(
            "Person", "Area", "Phone", "PhoneCall", "Vehicle",
            "Event", "Location", "Meeting", "Crime", "Officer",
        )
        val typeNames = arrayListOf(
            "KNOWS", "USES", "DEPENDS_ON", "IDENTIFIED_BY", "OWN_BY",
            "HAS_PHONE", "INVOLVED_IN", "LOCATED_IN", "PARTY_TO", "FAMILY_REL",
        )
        const val numOfAttributeSets = 100
        const val numOfNodes = 1_000
        const val numOfRelationships = 1_000
    }

    private val attributeSets = ArrayList<MutableMap<String, Any>>(numOfAttributeSets)
    private val nodeData = ArrayList<NodeData>(numOfNodes)
    private val relationshipData = ArrayList<RelationshipData>(numOfRelationships)
    private val faker = Faker()

    @BeforeAll
    fun generateNodesData(session: Session) {
        val objectApi = session.objectApi()
        val schemaApi = session.schemaApi()
        schemaApi.createClasses(classNames)
        schemaApi.createRelationshipTypes(typeNames)
        repeat(numOfAttributeSets) {
            val map = mutableMapOf<String, Any>()
            map["name"] = faker.name().fullName()
            map["address"] = faker.address().fullAddress()
            map["birthday"] = session.convertDate(faker.date().birthday())
            map["longitude"] = faker.random().nextInt(-180, 180)
            map["latitude"] = faker.random().nextInt(-90, 90)
            attributeSets += map
        }
        repeat(numOfNodes) { index ->
            val className = classNames[index % classNames.size]
            val nodeIdentifier = objectApi.createNode(className, emptyMap())
            nodeData += NodeData(nodeIdentifier, className)
        }
    }

    @Test
    @Order(1)
    fun `creating relationship`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        repeat(numOfRelationships) {
            val startIndex = faker.random().nextInt(numOfNodes)
            val endIndex = (startIndex + faker.random().nextInt(numOfNodes - 1)) % numOfNodes
            val typeName = typeNames[startIndex % typeNames.size]
            val startNode = nodeData[startIndex]
            val endNode = nodeData[endIndex]
            val relationIdentifier = testTimer.timeMeasureWithResult { objectApi.createRelationship(typeName, startNode.id, endNode.id, emptyMap()) }
            assertThat(relationIdentifier).isNotNull
            relationshipData += RelationshipData(relationIdentifier, typeName)
        }
    }

    @Test
    @Order(2)
    fun `update relationships`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        repeat(numOfRelationships) {
            val relationshipToUpdate = relationshipData[faker.random().nextInt(numOfRelationships)]
            val updateMap = attributeSets[faker.random().nextInt(numOfAttributeSets)]
            testTimer.timeMeasure { objectApi.updateRelationship(relationshipToUpdate.typeName, relationshipToUpdate.id, updateMap) }
        }
    }

    @Test
    @Order(3)
    fun `deleting relationships`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        relationshipData.forEach { relationship ->
            testTimer.timeMeasure { objectApi.deleteRelationship(relationship.typeName, relationship.id) }
        }
    }

    @AfterAll
    fun cleanUpData(session: Session) {
        val objectApi = session.objectApi()
        val schemaApi = session.schemaApi()
        nodeData.forEach { node ->
            objectApi.deleteNode(node.className, node.id)
        }
        typeNames.forEach { schemaApi.dropClass(it) }
        classNames.forEach { schemaApi.dropClass(it) }
    }

    private data class NodeData(val id: NodeIdentifier, val className: String)

    private data class RelationshipData(val id: RelationshipIdentifier, val typeName: String)
}

