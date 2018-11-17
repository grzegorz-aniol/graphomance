package org.gangel.graphomance.usecases.relation;

import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.usecases.TestBase;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CreateSingleEdgeBase extends TestBase {

  protected long numOfNodes = 1_000;
  protected final String CITY_CLASS = "City";
  protected final String ROADTO_CLASS = "RoadTo";
  protected final String NAME_PROP = "name";
  protected final String SIZE_PROP = "size";
  protected final List<NodeIdentifier> nodesId = new LinkedList<>();

  protected CreateSingleEdgeBase(final long numOfNodes) {
    this.numOfNodes = numOfNodes;
  }

  @Override
  public void setUpTest() {
    cleanUpAfter();

    schemaApi.createClass(CITY_CLASS);
    schemaApi.createProperty(CITY_CLASS, NAME_PROP, String.class, true);
    schemaApi.createProperty(CITY_CLASS, SIZE_PROP, Long.class, true );

    schemaApi.createRelationClass(ROADTO_CLASS);
  }

  @Override
  public void createTestData() {
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
  public void cleanUpAfter() {
    schemaApi.dropAllIndexesOnClass(CITY_CLASS);
    objectApi.deleteAllRelations(ROADTO_CLASS);
    objectApi.deleteAllNodes(CITY_CLASS);
    objectApi.deleteAllNodes(ROADTO_CLASS);
    schemaApi.dropClass(CITY_CLASS);
    schemaApi.dropClass(ROADTO_CLASS);
  }

}
