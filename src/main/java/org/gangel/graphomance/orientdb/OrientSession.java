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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public void close() {
        if (session != null) {
            session.close();
            session = null;
        }
        this.schemaApi = null;
        this.objectApi = null;
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

    @Override
    public void runScript(String script) {
        try(OResultSet rs = session.execute("sql", script)) {
        }
    }

    @Override
    public void runScriptFromResource(final String resourcePath) {
        InputStream resourceAsStream = OrientSession.class.getResourceAsStream(resourcePath);
        BufferedReader r = new BufferedReader(new InputStreamReader(resourceAsStream));
        StringBuilder sb = new StringBuilder();
        String s;
        try {
            while ((s = r.readLine()) != null) {
                sb.append(s);
            }
            r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (OResultSet rs = session.execute("sql", sb.toString())) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done.");
    }


}
