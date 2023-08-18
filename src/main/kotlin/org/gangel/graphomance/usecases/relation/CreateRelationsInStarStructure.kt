package org.gangel.graphomance.usecases.relation

import com.codahale.metrics.SharedMetricRegistries
import com.codahale.metrics.Timer
import org.assertj.core.api.Assertions
import org.gangel.graphomance.api.Session
import org.gangel.graphomance.engine.TestLimit
import java.time.Duration

class CreateRelationsInStarStructure :
	CreateSingleEdgeBase(SQUARE_SIZE * SQUARE_SIZE) {
	override fun performTest(session: Session) {
		val objectApi = session.objectApi()
		val schemaApi = session.schemaApi()
		val timerMetric = Timer()
		SharedMetricRegistries.getDefault()
			.register(this.javaClass.simpleName, timerMetric)
		var index = 0
		var count: Long = 0
		val limit =
			TestLimit((numOfNodes - 1).toLong(), Duration.ofSeconds(10), Duration.ofMinutes(10))

		// Id of big node
		val parentNodeId = nodesId[index++]
		while (!limit.isDone) {
			limit.increment()
			val sourceId = nodesId[index]
			val ts =
				if (++count > WARM_UP) timerMetric.time() else null
			val relationId = try {
				objectApi.createRelation(ROADTO_CLASS, sourceId, parentNodeId)
			} finally {
				ts?.close()
			}
			relationsId.add(relationId)

			// move to next node in the matrix
			index = (index + 1) % numOfNodes
		}
		System.out.printf("Created edges in test: %d\n", count)

		// verify results
		val createdEdges = schemaApi.countObjects(ROADTO_CLASS)
		Assertions.assertThat(createdEdges)
			.isEqualTo(count)
			.describedAs("Number of created edges should be as expected")
	}

	companion object {
		private const val SQUARE_SIZE = 100
		private const val WARM_UP = 100
	}
}