package org.gangel.graphomance.orientdb;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.ConnectionSettings;

@Builder
public class OrientConnectionSettings implements ConnectionSettings {

    @Getter
    private String mode;

    @Getter
    private String dbName;

    @Getter
    private String dbPath;

    @Getter
    private String dbUser;

    @Getter
    private String dbPassword;

    @Getter
    private String dbAdminUser;

    @Getter
    private String dbAdminPassword;
}
