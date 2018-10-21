package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.OrientDB;
import lombok.Builder;
import lombok.Getter;
import org.gangel.graphomance.Connection;

@Builder
public class OrientConnection implements Connection {

    private final OrientConnectionSettings opts;

    @Getter
    private OrientDB db;

    @Override
    public void open() {
        db.open(opts.getDbName(), opts.getDbAdminUser(), opts.getDbAdminPassword());
        registerShutdownHook(db);
    }

    @Override
    public void close() {
        db.close();
    }

    private static void registerShutdownHook( final OrientDB db )
    {
        // Registers a shutdown hook for the server instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread( () -> db.close() ));
    }

}
