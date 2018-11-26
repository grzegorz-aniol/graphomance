package org.gangel.graphomance.neo4j;

import lombok.Getter;
import org.gangel.graphomance.ManagementApi;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.SchemaApi;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.metrics.ObjectApiMetricsWrapper;
import org.neo4j.graphdb.GraphDatabaseService;

class NeoSession implements Session, ManagementApi {

    @Getter
    private NeoConnection connection;

    @Getter
    private GraphDatabaseService dbService;

    private SchemaApi schemaApi;

    private ObjectApi objectApi;

    NeoSession(NeoConnection connection) {
        this.connection = connection;
        this.dbService = connection.getDbService();
        this.schemaApi = new NeoSchemaApi(this);
        this.objectApi = ObjectApiMetricsWrapper.create(new NeoObjectApi(this));
    }

    @Override
    public void close() {

    }

    @Override
    public SchemaApi schemaApi() {
        return schemaApi;
    }

    @Override
    public ObjectApi objectApi() {
        return objectApi;
    }

    @Override
    public ManagementApi managementApi() {
        return this;
    }

    @Override
    public void runScript(String script) {
        throw new RuntimeException("Not supported!");
    }

    @Override
    public void runScriptFromResource(String resourcePath) {
        throw new RuntimeException("Not supported!");

    }
}
