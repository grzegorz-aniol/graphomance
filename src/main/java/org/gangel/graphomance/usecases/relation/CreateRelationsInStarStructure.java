package org.gangel.graphomance.usecases.relation;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.engine.TestLimit;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateRelationsInStarStructure extends CreateSingleEdgeBase {

  private static final int SQUARE_SIZE = 500;
  private static final int WARM_UP = 100;

  public CreateRelationsInStarStructure() {
    super(SQUARE_SIZE * SQUARE_SIZE);
  }

  @Override
  public void performTest() {
    Timer timerMetric = new Timer();
    SharedMetricRegistries.getDefault().register(this.getClass().getSimpleName(), timerMetric);

    int row = 0;
    int column = 1; // skip "big node" of the star which is at location (0,0)
    long count = 0;
    TestLimit limit = new TestLimit(numOfNodes - 1, Duration.ofSeconds(10), Duration.ofMinutes(10));

    // Id of big node
    NodeIdentifier parentNodeId = getNode(0, 0);

    while (!limit.isDone()) {
      limit.increment();

      NodeIdentifier sourceId = getNode(row, column);

      Timer.Context ts = (++count > WARM_UP ? timerMetric.time() : null);
      try {
        objectApi.createRelation(ROADTO_CLASS, sourceId, parentNodeId);
      } finally {
        if (ts != null) {
          ts.close();
        }
      }

      // move to next node in the matrix
      ++column;
      if (column >= SQUARE_SIZE) {
        column = 0;
        ++row;
        if (row >= SQUARE_SIZE) {
          row = 0;
        }
      }

    }

    System.out.printf("Created edges in test: %d\n", count);

    // verify results
    long createdEdges = schemaApi.countObjects(ROADTO_CLASS);
    assertThat(createdEdges).isEqualTo(count).describedAs("Number of created edges should be as expected");

  }

  int next(int value) {
    return (value + 1) % SQUARE_SIZE;
  }

  int prev(int value) {
    return value == 0 ? SQUARE_SIZE-1 : value - 1;
  }

  NodeIdentifier getNode(int row, int column) {
    int index = (row * SQUARE_SIZE) + column;
    return this.nodesId.get(index);
  }

}
