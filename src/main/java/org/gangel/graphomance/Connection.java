package org.gangel.graphomance;

import java.io.Closeable;

public interface Connection extends Closeable, AutoCloseable{

    void open();

    void close();


}
