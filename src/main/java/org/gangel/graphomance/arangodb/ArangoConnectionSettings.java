package org.gangel.graphomance.arangodb;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.ConnectionSettings;

@Builder
public class ArangoConnectionSettings implements ConnectionSettings {

    @Getter
    private String dbName = "test";

    @Getter
    private String user;

    @Getter
    private String password;
}
