package org.graphomance.usecases.crud

import com.github.javafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.graphomance.api.DbType
import org.graphomance.api.NodeIdentifier
import org.graphomance.api.Session
import org.graphomance.engine.GraphomanceTest
import org.graphomance.engine.QueryTimer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@GraphomanceTest(dbTargets = [DbType.NEO4J, DbType.MEMGRAPH, DbType.ARANGODB])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class NodeSimpleCrud {

    private companion object {
        val classNames = arrayListOf(
            "Person", "Area", "Phone", "PhoneCall", "Vehicle",
            "Event", "Location", "Meeting", "Crime", "Officer"
        )
        const val numOfAttributeSets = 100
        const val numOfNodes = 1_000
    }

    private val attributeSets = ArrayList<MutableMap<String, Any>>(numOfAttributeSets)
    private val nodeData = ArrayList<NodeData>(numOfNodes)
    private val faker = Faker()

    @BeforeAll
    fun generateNodesData(session: Session) {
        session.cleanDataInDatabase()
        session.schemaApi().createClasses(classNames)
        repeat(numOfAttributeSets) {
            val map = mutableMapOf<String, Any>()
            map["name"] = faker.name().fullName()
            map["address"] = faker.address().fullAddress()
            map["birthday"] = session.convertDate(faker.date().birthday())
            map["longitude"] = faker.random().nextInt(-180, 180)
            map["latitude"] = faker.random().nextInt(-90, 90)
            attributeSets += map
        }
    }

    @Test
    @Order(1)
    fun `creating node with properties`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        repeat(numOfNodes) {
            val index = faker.random().nextInt(numOfAttributeSets)
            val clasName = classNames[index % classNames.size]
            val nodeIdentifier = testTimer.timeMeasureWithResult { objectApi.createNode(clasName, attributeSets[index]) }
            assertThat(nodeIdentifier).isNotNull
            nodeData += NodeData(nodeIdentifier, clasName)
        }
    }

    @Test
    @Order(2)
    fun `updating node`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        repeat(numOfNodes) {
            val index = faker.random().nextInt(numOfNodes)
            val nodeToUpdate = nodeData[index]
            val newDataIndex = (faker.random().nextInt(numOfAttributeSets)) % numOfAttributeSets
            val newData = attributeSets[newDataIndex]
            testTimer.timeMeasure { objectApi.updateNode(nodeToUpdate.className, nodeToUpdate.id, newData) }
        }
    }

    @Test
    @Order(3)
    fun `deleting nodes`(session: Session, testTimer: QueryTimer) {
        val objectApi = session.objectApi()
        nodeData.forEach { node ->
            testTimer.timeMeasure {
                objectApi.deleteNode(node.className, node.id)
            }
        }
    }

    private data class NodeData(val id: NodeIdentifier, val className: String)

}