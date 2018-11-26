package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.ManagementApi;
import org.gangel.graphomance.ObjectApi;
import org.gangel.graphomance.SchemaApi;
import org.gangel.graphomance.Session;
import org.gangel.graphomance.metrics.ObjectApiMetricsWrapper;

@Builder
public class ArangoSession implements Session {

    @Getter
    private ArangoDatabase db;

    @Getter
    private ArangoConnectionSettings connectionSettings;

    @Override
    public void close() {
    }

    @Override
    public SchemaApi schemaApi() {
        return ArangoSchemaApi.builder().db(db).build();
    }

    @Override
    public ObjectApi objectApi() {
        return ObjectApiMetricsWrapper.create(ArangoObjectApi.builder().db(db).build());
    }

    @Override
    public ManagementApi managementApi() {
        return ArangoManagementApi.builder().db(db).build();
    }

}
