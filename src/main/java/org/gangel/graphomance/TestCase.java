package org.gangel.graphomance;

public interface TestCase {

    void initialize(Connection conn, SessionProducer sessionProducer);

    void setUpTest();

    void createTestData();

    void performTest();

    void cleanUpAfter();

    void terminate();

    DbType[] skipFor();

    default boolean skipFor(DbType dbType) {
        DbType[] skipForTypes = skipFor();
        if (skipForTypes == null) {
            return false;
        }
        for(DbType t : skipForTypes) {
            if (t == dbType) {
                return true;
            }
        }
        return false;
    }

}
