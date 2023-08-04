package org.gangel.graphomance.usecases.relation

import org.gangel.graphomance.NodeIdentifier
import org.gangel.graphomance.RelationIdentifier
import org.gangel.graphomance.Session
import org.gangel.graphomance.engine.TestLimit
import org.gangel.graphomance.usecases.TestBase
import java.time.Duration
import java.util.LinkedHashMap
import java.util.LinkedList
import java.util.Objects
import java.util.Random

abstract class CreateSingleEdgeBase protected constructor(protected val numOfNodes: Int = 1000) : TestBase() {
    protected val CITY_CLASS = "City"
    protected val ROADTO_CLASS = "RoadTo"
    protected val NAME_PROP = "name"
    protected val SIZE_PROP = "size"
    protected val nodesId = mutableListOf<NodeIdentifier>()
    protected val relationsId = mutableListOf<RelationIdentifier>()
    override fun setUpTest(session: Session) {}
    override fun createTestData(session: Session) {
        val schemaApi = session.schemaApi()
        val objectApi = session.objectApi()
        schemaApi.createClass(CITY_CLASS)
//		schemaApi.createProperty(CITY_CLASS, NAME_PROP, String::class.java, true)
//		schemaApi.createProperty(CITY_CLASS, SIZE_PROP, Long::class.java, true)
        schemaApi.createRelationClass(ROADTO_CLASS)
        objectApi.startTransaction()
        try {
            for (i in 0 until numOfNodes) {
                nodesId.add(objectApi.createNode(CITY_CLASS, mapOf(NAME_PROP to "CityName_$i", SIZE_PROP to (i + 1) * 5247)))
            }
            objectApi.commit()
        } catch (e: Throwable) {
            objectApi.rollback()
            throw e
        }
    }

    override fun cleanUpData(session: Session) {
        val objectApi = session.objectApi()
        val schemaApi = session.schemaApi()
        objectApi.deleteAllRelations(ROADTO_CLASS)
        objectApi.deleteAllNodes(CITY_CLASS)
        schemaApi.dropClass(CITY_CLASS)
        schemaApi.dropClass(ROADTO_CLASS)
    }

    fun testStageReadNodes(session: Session) {
        val objectApi = session.objectApi()
        for (i in 0 until numOfNodes) {
            val obj = objectApi.getNode(CITY_CLASS, nodesId[i])
            Objects.requireNonNull(obj)
        }
    }

    override val stages: Map<String, (Session)->Unit>
        get() = mapOf("Create relations" to ::performTest,
                "Read nodes" to ::testStageReadNodes,
                "Query paths" to ::testStageQueryPaths,
                "Update nodes" to ::testStateUpdateNodes,
                "Update relations" to ::testStateUpdateRelations,
                "Delete relations" to ::testStageDeleteRelations,
                "Delete nodes" to ::testStageDeleteNodes,
        )

    fun testStageQueryPaths(session: Session) {
        val objectApi = session.objectApi()
        val r = Random()
        var i = 0
        while (i < 10 * Math.sqrt(numOfNodes.toDouble())) {
            val idx1 = r.nextInt(numOfNodes)
            val idx2 = r.nextInt(numOfNodes)
            val length = objectApi.shortestPath(nodesId[idx1], nodesId[idx2])
            if (length < 0) {
                throw RuntimeException("Shortest path return unknown number")
            }
            ++i
        }
    }

    fun testStateUpdateNodes(session: Session) {
        val objectApi = session.objectApi()
        var value: Long = 0
        val limit = TestLimit(relationsId.size.toLong(), Duration.ofMinutes(1), Duration.ofMinutes(1))
        for (id in nodesId) {
            if (limit.isDone) {
                return
            }
            limit.increment()
            value += 1
            objectApi.updateNode(CITY_CLASS, id, mapOf("value" to value))
        }
    }

    fun testStateUpdateRelations(session: Session) {
        val objectApi = session.objectApi()
        var value: Long = 0
        val limit = TestLimit(relationsId.size.toLong(), Duration.ofMinutes(1), Duration.ofMinutes(1))
        for (id in relationsId) {
            if (limit.isDone) {
                return
            }
            limit.increment()
            value += 1
            objectApi.updateRelation(ROADTO_CLASS, id, mapOf("value" to value))
        }
    }

    fun testStageDeleteRelations(session: Session) {
        session.objectApi().deleteAllRelations(ROADTO_CLASS)
    }

    fun testStageDeleteNodes(session: Session) {
        val objectApi = session.objectApi()
        objectApi.deleteAllNodes(CITY_CLASS)
        objectApi.deleteAllNodes(ROADTO_CLASS)
    }

}