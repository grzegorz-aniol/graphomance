package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import org.gangel.graphomance.Connection;
import org.gangel.graphomance.ConnectionProducer;
import org.gangel.graphomance.ConnectionSettings;

import java.util.Objects;

public class OrientConnectionProducer implements ConnectionProducer {

    @Override
    public Connection connect(ConnectionSettings settings) {
        OrientConnectionSettings opts = Objects.requireNonNull((OrientConnectionSettings)settings);
        return OrientConnection.builder()
                .db(new OrientDB(opts.getMode() + ":" + opts.getDbPath().replace("\\","/"),
                        opts.getDbAdminUser(), opts.getDbAdminPassword(),
                        OrientDBConfig.defaultConfig()))
                .opts((OrientConnectionSettings)settings)
                .build();
    }
}
