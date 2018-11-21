package org.gangel.graphomance.usecases;

import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Map;

public class CreateBasicRelationTest extends TestBase {

    NodeIdentifier nodeId1;
    NodeIdentifier nodeId2;
    RelationIdentifier friendOf;

    @Override
    public void setUpTest() {
    }

    @Override
    public void createTestData() {
        schemaApi.createClass("Person");
        schemaApi.createRelationClass("FriendOf");
    }

    @Override
    public void performTest() {
        nodeId1 = objectApi.createNode("Person", Map.of("name", "John"));
        nodeId2 = objectApi.createNode("Person", Map.of("name", "Kate"));
        friendOf = objectApi.createRelation("FriendOf", nodeId1, nodeId2);
    }

    @Override
    public void cleanUpData() {
        objectApi.deleteAllRelations("FriendOf");
        objectApi.deleteAllNodes("Person");
        schemaApi.dropClass("Person");
        schemaApi.dropClass("FriendOf");
    }
}
