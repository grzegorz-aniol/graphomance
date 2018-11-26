package org.gangel.graphomance;

import java.util.Map;

public interface ObjectApi {

  void startTransaction();

  void commit();

  void rollback();

  Object getNode(final String clsName, final NodeIdentifier nodeId);

  long shortestPath(final NodeIdentifier start, final NodeIdentifier end);

  default NodeIdentifier createNode(final String clsName) {
    return createNode(clsName, null);
  }

  NodeIdentifier createNode(final String clsName, Map<String, Object> properties);

  void updateNode(final String clsName, NodeIdentifier nodeId, Map<String, Object> properties);

  default RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode) {
    return createRelation(className, fromNode, toNode, null);
  }

  RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode,
                                    Map<String, Object> properties);

  void updateRelation(final String clsName, RelationIdentifier edgeId, Map<String, Object> properties);

  void deleteAllNodes(final String clsName);

  void deleteAllRelations(final String clsName);
}
