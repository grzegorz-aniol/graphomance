package org.gangel.graphomance;

import java.io.Closeable;
import java.util.Map;

public interface Session extends AutoCloseable, Closeable {

    void open();

    void close();

    SchemaApi schemaApi();

    ObjectApi objectApi();

    ManagementApi managementApi();

}
