package org.gangel.graphomance.usecases.relation

import com.codahale.metrics.SharedMetricRegistries
import com.codahale.metrics.Timer
import org.assertj.core.api.Assertions
import org.gangel.graphomance.api.NodeIdentifier
import org.gangel.graphomance.api.Session
import org.gangel.graphomance.engine.TestLimit
import java.time.Duration

class CreateRelationsInFlatStructure :
        CreateSingleEdgeBase(SQUARE_SIZE * SQUARE_SIZE) {
    override fun performTest(session: Session) {
        val objectApi = session.objectApi()
        val schemaApi = session.schemaApi()

        val timerMetric = Timer()
        SharedMetricRegistries.getDefault()
                .register(this.javaClass.simpleName, timerMetric)
        var row = 0
        var column = 0
        var count: Long = 0
        val limit = TestLimit(numOfNodes.toLong(), Duration.ofSeconds(10), Duration.ofMinutes(10))
        while (!limit.isDone) {
            limit.increment()
            val sourceId = getNode(row, column)
            val nodes = listOf(getNode(row, next(column)),
                    getNode(row, prev(column)),
                    getNode(next(row), column),
                    getNode(prev(row), column))
            for (nodeId in nodes) {
                val ts =
                        if (++count > WARM_UP) timerMetric.time() else null
                val relationId = try {
                    objectApi.createRelation(ROADTO_CLASS, sourceId, nodeId)
                } finally {
                    ts?.close()
                }
                relationsId.add(relationId)
            }

            // move to next node in the matrix
            ++column
            if (column >= SQUARE_SIZE) {
                column = 0
                ++row
                if (row >= SQUARE_SIZE) {
                    row = 0
                }
            }
        }
        System.out.printf("Created edges in test: %d\n", count)

        // verify results
        val createdEdges = schemaApi!!.countObjects(ROADTO_CLASS)
        Assertions.assertThat(createdEdges)
                .isEqualTo(count)
                .describedAs("Number of created edges should be as expected")
    }

    fun next(value: Int): Int {
        return (value + 1) % SQUARE_SIZE
    }

    fun prev(value: Int): Int {
        return if (value == 0) SQUARE_SIZE - 1 else value - 1
    }

    fun getNode(row: Int, column: Int): NodeIdentifier {
        val index = row * SQUARE_SIZE + column
        return nodesId[index]
    }

    companion object {
        private const val SQUARE_SIZE = 100
        private const val WARM_UP = 10
    }
}