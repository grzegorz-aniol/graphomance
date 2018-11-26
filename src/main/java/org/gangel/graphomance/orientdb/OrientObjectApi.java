package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import org.gangel.graphomance.ManagementApi;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.RelationIdentifier;

import java.util.Map;
import java.util.Objects;

public class OrientObjectApi implements ObjectApi {

  private final ODatabaseSession session;
  private final ManagementApi managementApi;

  OrientObjectApi(ODatabaseSession session, ManagementApi managementApi) {
    this.session = session;
    this.managementApi = managementApi;
  }

  @Override
  public void startTransaction() {
    session.begin();
  }

  @Override
  public void commit() {
    session.commit();
  }

  @Override
  public void rollback() {
    session.rollback();
  }

  @Override
  public Object getNode(String clsName, NodeIdentifier nodeId) {
    try (OResultSet rs = session.query("select @rid from :id", NodeId.getORID(nodeId)) ) {
      if (rs.hasNext()) {
        return rs.next();
      }
    }
    return null;
  }

  @Override
  public long shortestPath(NodeIdentifier start, NodeIdentifier end) {
    try (OResultSet rs = session.query("select shortestpath(:n1, :n2).size() as length",
        Map.of("n1", NodeId.getORID(start),
               "n2", NodeId.getORID(end))) ) {
      if (rs.hasNext()) {
        return (Integer)rs.next().getProperty("length");
      }
    }
    return -1;
  }

  @Override
  public NodeIdentifier createNode(String className, Map<String, Object> properties) {
    OVertex oVertex = session.newVertex(className);
    properties.forEach((k,v) -> {
      oVertex.setProperty(k,v);
    });
    ORID id = Objects.requireNonNull(oVertex.save().getIdentity(), "Can't read new node ID");
    return NodeId.build(id);
  }

  private boolean updateDocument(String clsName, ORID docId, Map<String, Object> properties) {
    if (properties == null || properties.size() == 0) {
      return false;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("update ").append(clsName).append(" set ");
    boolean isFirst = true;
    for(Map.Entry<String,Object> entry : properties.entrySet()) {
      if (!isFirst) {
        sb.append(", ");
      }
      isFirst = false;
      sb.append(entry.getKey()).append("=:").append(entry.getKey()).append(" ");
    }
    sb.append(" where @rid=").append(docId);

    try (OResultSet command = session.command(sb.toString(), properties)) {
      if (command.hasNext()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void updateNode(String clsName, NodeIdentifier nodeId, Map<String, Object> properties) {
    if (!updateDocument(clsName, NodeId.getORID(nodeId), properties)) {
      throw new RuntimeException("Can't update vertex");
    }
  }

  @Override
  public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode,
                                           Map<String, Object> properties) {
    try (OResultSet command = session.command(String.format("create edge %s from :f to :t", className),
          Map.of("f", NodeId.getORID(fromNode),
        "t", NodeId.getORID(toNode)))) {
      OResult res = Objects.requireNonNull(command.next(), "Can't read new relation ID");
      return EdgeId.build(res.getIdentity().get());
    }

  }

  @Override
  public void updateRelation(String clsName, RelationIdentifier edgeId, Map<String, Object> properties) {
    if (!updateDocument(clsName, EdgeId.getORID(edgeId), properties)) {
      throw new RuntimeException("Can't update edge");
    }
  }

  @Override
  public void deleteAllNodes(String clsName) {
    if (session.getClass(clsName) == null) {
      return;
    }
    try (OResultSet command = session.command(String.format("delete vertex %s", clsName) )) {
    }
  }

  @Override
  public void deleteAllRelations(String clsName) {
    if (session.getClass(clsName) == null) {
      return;
    }
    try (OResultSet command = session.command(String.format("delete edge %s", clsName) )) {
    }
  }


}
