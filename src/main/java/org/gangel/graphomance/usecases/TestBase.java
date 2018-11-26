package org.gangel.graphomance.usecases;

import org.gangel.graphomance.*;

import java.util.LinkedHashMap;
import java.util.Objects;

public abstract class TestBase implements TestCase {

    protected Connection conn;

    protected SessionProducer sessionProducer;

    protected Session session;

    protected ObjectApi objectApi;
    protected SchemaApi schemaApi;

    protected LinkedHashMap<String, Runnable> stageMapBuilder(String testName, Runnable stage, Object... args) {
        LinkedHashMap<String, Runnable> map = new LinkedHashMap<>();
        map.put(testName, stage);
        if (args.length % 2 !=0 ){
            throw new RuntimeException("Odd number of arguments provider. Please specify pair of stage name and runnable body");
        }
        for(int i=0; i < args.length; i+=2) {
            String n = Objects.requireNonNull((String)args[i]);
            Runnable s = Objects.requireNonNull((Runnable)args[i+1]);
            map.put(n, s);
        }
        return map;
    }

    public LinkedHashMap<String, Runnable> getStages() {
        return stageMapBuilder(this.getClass().getSimpleName(), () -> performTest());
    }

    @Override
    public void initialize(Connection conn, SessionProducer sessionProducer) {
        this.conn = conn;
        this.sessionProducer = sessionProducer;
        this.session = sessionProducer.createSession(conn);
        objectApi = session.objectApi();
        schemaApi = session.schemaApi();
    }

    @Override
    public void terminate() {
        this.session.close();
    }

    @Override
    public DbType[] skipFor() {
        return null;
    }
}
