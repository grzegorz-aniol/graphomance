package org.graphomance.usecases.crud

import com.github.javafaker.Faker
import java.time.LocalDateTime
import java.time.ZoneOffset
import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.NodeIdentifier
import org.graphomance.api.RelationshipIdentifier
import org.graphomance.api.Session
import org.graphomance.engine.GraphomanceTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@GraphomanceTest(customDatabaseName = "pole", dbTargets = [DbType.NEO4J, DbType.MEMGRAPH])
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
        const val numOfNodes = 100
        const val numOfRelationships = 100
    }

    private val attributeSets = ArrayList<MutableMap<String, Any>>(numOfAttributeSets)
    private val nodeData = ArrayList<NodeData>(numOfNodes)
    private val relationshipData = ArrayList<RelationshipData>(numOfRelationships)
    private val faker = Faker()

    @BeforeAll
    fun generateNodesData(session: Session) {
        val objectApi = session.objectApi()
        repeat(numOfAttributeSets) { index ->
            val map = mutableMapOf<String, Any>()
            map["name"] = faker.name().fullName()
            map["address"] = faker.address().fullAddress()
            map["birthday"] =
                LocalDateTime.from(faker.date().birthday(18, 90).toInstant().atZone(ZoneOffset.UTC)).toLocalDate()
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

    @RepeatedTest(value = numOfRelationships)
    @Order(1)
    fun `creating relationship`(session: Session) {
        val startIndex = faker.random().nextInt(numOfNodes)
        val endIndex = (startIndex + faker.random().nextInt(numOfNodes - 1)) % numOfNodes
        val typeName = typeNames[startIndex % typeNames.size]
        val startNode = nodeData[startIndex]
        val endNode = nodeData[endIndex]
        val relationIdentifier = session.objectApi().createRelationship(typeName, startNode.id, endNode.id, emptyMap())
        assertThat(relationIdentifier).isNotNull
        relationshipData += RelationshipData(relationIdentifier, typeName)
    }

    @RepeatedTest(value = numOfRelationships)
    @Order(2)
    fun `update relationships`(session: Session) {
        val relationshipToUpdate = relationshipData[faker.random().nextInt(numOfRelationships)]
        val updateMap = attributeSets[faker.random().nextInt(numOfAttributeSets)]
        session.objectApi().updateRelationship(relationshipToUpdate.typeName, relationshipToUpdate.id, updateMap)
    }

    @Test
    @Order(3)
    fun `deleting relationships`(session: Session) {
        val objectApi = session.objectApi()
        relationshipData.forEach { relationship ->
            objectApi.deleteRelationship(relationship.typeName, relationship.id)
        }
    }

    @AfterAll
    fun cleanUpData(session: Session) {
        val objectApi = session.objectApi()
        nodeData.forEach { node ->
            objectApi.deleteNode(node.className, node.id)
        }
    }

    private data class NodeData(val id: NodeIdentifier, val className: String)

    private data class RelationshipData(val id: RelationshipIdentifier, val typeName: String)

}