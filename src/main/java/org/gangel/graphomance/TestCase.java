package org.gangel.graphomance;

public interface TestCase {

    void initialize(Connection conn, SessionProducer sessionProducer);

    void setUpTest();

    void createTestData();

    void performTest();

    void cleanUpAfter();

    void terminate();

}
