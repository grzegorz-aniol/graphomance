package org.gangel.graphomance.usecases.relation;

import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;
import org.gangel.graphomance.engine.TestLimit;
import org.gangel.graphomance.usecases.TestBase;

import java.time.Duration;
import java.util.*;

public abstract class CreateSingleEdgeBase extends TestBase {

  protected final String CITY_CLASS = "City";
  protected final String ROADTO_CLASS = "RoadTo";
  protected final String NAME_PROP = "name";
  protected final String SIZE_PROP = "size";
  protected final List<NodeIdentifier> nodesId = new LinkedList<>();
  protected final List<RelationIdentifier> relationsId = new LinkedList<>();
  protected int numOfNodes = 1_000;

  protected CreateSingleEdgeBase(final int numOfNodes) {
    this.numOfNodes = numOfNodes;
  }

  @Override
  public void setUpTest() {
  }

  @Override
  public void createTestData() {
    schemaApi.createClass(CITY_CLASS);
    schemaApi.createProperty(CITY_CLASS, NAME_PROP, String.class, true);
    schemaApi.createProperty(CITY_CLASS, SIZE_PROP, Long.class, true );

    schemaApi.createRelationClass(ROADTO_CLASS);

    objectApi.startTransaction();
    try {
      for(int i = 0; i < numOfNodes; ++i) {
        nodesId.add(objectApi.createNode(CITY_CLASS,
            Map.of(NAME_PROP, "CityName_" + String.valueOf(i),
                SIZE_PROP, (i+1) * 5247)));
      }
      objectApi.commit();

    } catch(Throwable e) {
      objectApi.rollback();
      throw e;
    }
  }

  @Override
  public void cleanUpData() {
    objectApi.deleteAllRelations(ROADTO_CLASS);
    objectApi.deleteAllNodes(CITY_CLASS);
    schemaApi.dropClass(CITY_CLASS);
    schemaApi.dropClass(ROADTO_CLASS);
  }

  @Override
  public LinkedHashMap<String, Runnable> getStages() {
    return stageMapBuilder(
        "Create relations", this::performTest,
        "Read nodes", (Runnable) this::testStageReadNodes,
        "Query paths", (Runnable) this::testStageQueryPaths,
        "Update nodes", (Runnable) this::testStateUpdateNodes,
        "Update relations", (Runnable) this::testStateUpdateRelations,
        "Drop indexes", (Runnable) this::testStageDropIndexes,
        "Delete relations", (Runnable) this::testStageDeleteRelations,
        "Delete nodes", (Runnable) this::testStageDeleteNodes
    );
  }

  public void testStageReadNodes() {
    for (int i=0; i < numOfNodes; ++i) {
      Object obj = objectApi.getNode(CITY_CLASS, nodesId.get(i));
      Objects.requireNonNull(obj);
    }
  }

  public void testStageQueryPaths() {
    Random r = new Random();
    for (int i = 0; i < 10*Math.sqrt(numOfNodes); ++i) {
      int idx1 = r.nextInt(numOfNodes);
      int idx2 = r.nextInt(numOfNodes);
      long length = objectApi.shortestPath(nodesId.get(idx1), nodesId.get(idx2));
      if (length < 0) {
        throw new RuntimeException("Shortest path return unknown number");
      }
    }
  }

  public void testStateUpdateNodes() {
    long value = 0;
    TestLimit limit = new TestLimit(relationsId.size(), Duration.ofMinutes(1), Duration.ofMinutes(1));
    for (NodeIdentifier id : nodesId) {
      if (limit.isDone()) {
        return;
      }
      limit.increment();
      value += 1;
      objectApi.updateNode( CITY_CLASS, id, Map.of("value", value));
    }
  }

  public void testStateUpdateRelations() {
    long value = 0;
    TestLimit limit = new TestLimit(relationsId.size(), Duration.ofMinutes(1), Duration.ofMinutes(1));
    for (RelationIdentifier id : relationsId) {
      if (limit.isDone()) {
        return;
      }
      limit.increment();
      value += 1;
      objectApi.updateRelation( ROADTO_CLASS, id, Map.of("value", value));
    }
  }


  public void testStageDropIndexes() {
    schemaApi.dropAllIndexesOnClass(CITY_CLASS);
  }

  public void testStageDeleteRelations() {
    objectApi.deleteAllRelations(ROADTO_CLASS);
  }

  public void testStageDeleteNodes() {
    objectApi.deleteAllNodes(CITY_CLASS);
    objectApi.deleteAllNodes(ROADTO_CLASS);
  }

}
