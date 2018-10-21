package org.gangel.graphomance.neo4j;

import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.ConnectionSettings;

@Builder
public class NeoConnectionSettings implements ConnectionSettings {

    @Getter
    private String dbPath;

}
