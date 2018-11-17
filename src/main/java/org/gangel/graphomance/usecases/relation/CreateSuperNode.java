package org.gangel.graphomance.usecases.relation;

import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.engine.TestLimit;

import java.time.Duration;

public class CreateSuperNode extends CreateSingleEdgeBase {

  private static final int SQUARE_SIZE = 100;
  private static final int WARM_UP = 10;

  public CreateSuperNode() {
    super(SQUARE_SIZE * SQUARE_SIZE);
  }

  @Override
  public void performTest() {
    Timer timerMetric = new Timer();
    SharedMetricRegistries.getDefault().register(this.getClass().getSimpleName(), timerMetric);

    int row = 0;
    int column = 0;
    long count = 0;
    NodeIdentifier[] nodes = new NodeIdentifier[4];
    TestLimit limit = new TestLimit(numOfNodes, Duration.ofSeconds(60), Duration.ofMinutes(10));

    while (!limit.isDone()) {
      limit.increment();

      NodeIdentifier sourceId = getNode(row, column);

      nodes[0] = getNode(row, next(column) );
      nodes[1] = getNode(row, prev(column) );
      nodes[2] = getNode(next(row), column );
      nodes[3] = getNode(prev(row), column );

      for (NodeIdentifier nodeId : nodes) {
        Timer.Context ts = (++count > WARM_UP ? timerMetric.time() : null);
        try {
          objectApi.createRelation(ROADTO_CLASS, sourceId, nodeId);
        } finally {
          if (ts != null) {
            ts.close();
          }
        }
      }
    }
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
