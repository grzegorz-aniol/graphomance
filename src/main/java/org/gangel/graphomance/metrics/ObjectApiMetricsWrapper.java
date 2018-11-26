package org.gangel.graphomance.metrics;

import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Map;

public class ObjectApiMetricsWrapper implements ObjectApi {

  private ObjectApi delegate;

  public ObjectApiMetricsWrapper(ObjectApi delegate) {
    this.delegate = delegate;
  }

  public static ObjectApiMetricsWrapper create(ObjectApi delegate) {
    return new ObjectApiMetricsWrapper(delegate);
  }

  @Override
  public void startTransaction() {
    delegate.startTransaction();
  }

  @Override
  public void commit() {
    delegate.commit();
  }

  @Override
  public void rollback() {
    delegate.rollback();
  }

  @Override
  public Object getNode(String clsName, NodeIdentifier nodeId) {
    return Metrics.measure(Metrics.GET_NODE, () -> delegate.getNode(clsName, nodeId));
  }

  @Override
  public long shortestPath(NodeIdentifier start, NodeIdentifier end) {
    return Metrics.measure(Metrics.FIND_SHORTEST_PATH, () -> delegate.shortestPath(start, end));
  }

  @Override
  public NodeIdentifier createNode(String clsName, Map<String, Object> properties) {
    return Metrics.measure(Metrics.CREATE_NODE, () -> delegate.createNode(clsName, properties));
  }

  @Override
  public void updateNode(String clsName, NodeIdentifier nodeId, Map<String, Object> properties) {
    Metrics.measure(Metrics.UPDATE_NODE, () -> delegate.updateNode(clsName, nodeId, properties));
  }

  @Override
  public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode, Map<String, Object> properties) {
    return Metrics.measure(Metrics.CREATE_RELATION, () -> delegate.createRelation(className, fromNode, toNode, properties));
  }

  @Override
  public void updateRelation(String clsName, RelationIdentifier edgeId, Map<String, Object> properties) {
    Metrics.measure(Metrics.UPDATE_RELATION, () -> delegate.updateRelation(clsName, edgeId, properties));
  }

  @Override
  public void deleteAllNodes(String clsName) {
    delegate.deleteAllNodes(clsName);
  }

  @Override
  public void deleteAllRelations(String clsName) {
    delegate.deleteAllRelations(clsName);
  }

}
