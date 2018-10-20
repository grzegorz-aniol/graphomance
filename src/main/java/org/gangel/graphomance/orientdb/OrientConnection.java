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
    }

    @Override
    public void close() {
        db.close();
    }

}
