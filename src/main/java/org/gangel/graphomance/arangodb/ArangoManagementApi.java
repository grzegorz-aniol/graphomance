package org.gangel.graphomance.arangodb;

import com.arangodb.ArangoDatabase;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.ManagementApi;

@Builder
public class ArangoManagementApi implements ManagementApi {

    @Getter
    private ArangoDatabase db;

    @Override
    public void runScript(String script) {

    }

    @Override
    public void runScriptFromResource(String resourcePath) {

    }
}
