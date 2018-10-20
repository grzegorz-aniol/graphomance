package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.*;
import org.gangel.graphomance.metrics.Metrics;

import java.util.Map;
import java.util.Objects;

public class OrientSession implements Session, ManagementApi {

    @Getter
    private ODatabaseSession session;

    private OrientSchemaApi schemaApi;

    private OrientObjectApi objectApi;

    OrientSession(ODatabaseSession session) {
        this.session = session;
        this.schemaApi = new OrientSchemaApi(session, this);
        this.objectApi = new OrientObjectApi(session, this);
    }

    @Override
    public void open() {
    }

    @Override
    public void close() {
        session.close();
        session = null;
        this.schemaApi = null;
        this.objectApi = null;
    }


    public void runScript(String script) {
        try(OResultSet rs = session.execute("sql", script)) {
        }
    }

    @Override
    public SchemaApi schemaApi() {
        return this.schemaApi;
    }

    @Override
    public ObjectApi objectApi() {
        return this.objectApi;
    }

    @Override
    public ManagementApi managementApi() {
        return this;
    }

}
