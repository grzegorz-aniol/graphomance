package org.gangel.graphomance;

import java.io.Closeable;
import java.util.Map;

public interface Session extends AutoCloseable, Closeable {

    void open();

    void close();

    void runScript(String script);

    default NodeIdentifier createNode(String className) {
        return createNode(className, null);
    }

    NodeIdentifier createNode(String className, Map<String, Object> properties);

    default RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode) {
        return createRelation(className, fromNode, toNode, null);
    }

    RelationIdentifier createRelation(String className, NodeIdentifier fromNode, NodeIdentifier toNode,
                                      Map<String, Object> properties);

}
