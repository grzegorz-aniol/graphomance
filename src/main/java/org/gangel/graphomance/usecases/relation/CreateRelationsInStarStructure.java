package org.gangel.graphomance.usecases.relation;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;
import org.gangel.graphomance.engine.TestLimit;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateRelationsInStarStructure extends CreateSingleEdgeBase {

  private static final int SQUARE_SIZE = 100;
  private static final int WARM_UP = 100;

  public CreateRelationsInStarStructure() {
    super(SQUARE_SIZE * SQUARE_SIZE);
  }

  @Override
  public void performTest() {
    Timer timerMetric = new Timer();
    SharedMetricRegistries.getDefault().register(this.getClass().getSimpleName(), timerMetric);

    int index = 0;
    long count = 0;
    TestLimit limit = new TestLimit(numOfNodes - 1, Duration.ofSeconds(10), Duration.ofMinutes(10));

    // Id of big node
    NodeIdentifier parentNodeId = nodesId.get(index++);

    while (!limit.isDone()) {
      limit.increment();

      NodeIdentifier sourceId = nodesId.get(index);
      RelationIdentifier relationId;
      Timer.Context ts = (++count > WARM_UP ? timerMetric.time() : null);
      try {
        relationId = objectApi.createRelation(ROADTO_CLASS, sourceId, parentNodeId);
      } finally {
        if (ts != null) {
          ts.close();
        }
      }
      relationsId.add(relationId);

      // move to next node in the matrix
      index = (index + 1) % numOfNodes;

    }

    System.out.printf("Created edges in test: %d\n", count);

    // verify results
    long createdEdges = schemaApi.countObjects(ROADTO_CLASS);
    assertThat(createdEdges).isEqualTo(count).describedAs("Number of created edges should be as expected");

  }

}
