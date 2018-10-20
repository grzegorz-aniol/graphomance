package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.NodeIdentifier;
import org.gangel.graphomance.RelationIdentifier;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.metrics.Metrics;

import java.util.Map;
import java.util.Objects;

@Builder
public class OrientSession implements Session {

    @Getter
    private ODatabaseSession session;

    @Override
    public void open() {
    }

    @Override
    public void close() {
        session.close();
    }

    public void runScript(String script) {
        try(OResultSet rs = session.execute("sql", script)) {
        }
    }

    public NodeIdentifier createNode(String className, Map<String, Object> properties) {
        OVertex oVertex = session.newVertex(className);
        properties.forEach((k,v) -> {
            oVertex.setProperty(k,v);
        });
        ORID id = oVertex.save().getIdentity();
        Metrics.CREATED_VERTICES_METER.mark();
        return NodeId.build(id);
        /*
        try (OResultSet command = session.command(String.format("create vertex %s", className))) {
            OResult res = Objects.requireNonNull(command.next());
            return NodeId.build(res.getIdentity().get());
        }
        */
    }

    public RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode,
                                      Map<String, Object> properties) {
        try (OResultSet command = session.command(String.format("create edge %s", className))) {
            OResult res = Objects.requireNonNull(command.next());
            return EdgeId.build(res.getIdentity().get());
        }

    }

}
