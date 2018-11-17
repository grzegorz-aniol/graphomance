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
import org.gangel.graphomance.metrics.Metrics;

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
  public NodeIdentifier createNode(String className, Map<String, Object> properties) {
    OVertex oVertex = session.newVertex(className);
    properties.forEach((k,v) -> {
      oVertex.setProperty(k,v);
    });
    ORID id = Objects.requireNonNull(oVertex.save().getIdentity(), "Can't read new node ID");
    Metrics.CREATED_VERTICES_METER.mark();
    return NodeId.build(id);
        /*
        try (OResultSet command = session.command(String.format("create node %s", className))) {
            OResult res = Objects.requireNonNull(command.next());
            return NodeId.build(res.getIdentity().get());
        }
        */
  }

  @Override
  public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode,
                                           Map<String, Object> properties) {
    try (OResultSet command = session.command(String.format("create edge %s from :f to :t", className),
          Map.of("f", NodeId.getORID(fromNode),
        "t", NodeId.getORID(toNode)))) {
      OResult res = Objects.requireNonNull(command.next(), "Can't read new relation ID");
      Metrics.CREATED_RELATION_METER.mark();
      return EdgeId.build(res.getIdentity().get());
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
