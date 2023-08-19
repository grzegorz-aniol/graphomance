package org.graphomance.engine

import org.graphomance.api.DbType
import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@ExtendWith(GraphomanceExtension::class)
annotation class GraphomanceTest(
    val customDatabaseName: String = "",
    val dbTargets: Array<DbType> = [DbType.NEO4J, DbType.ARANGODB, DbType.MEMGRAPH]
)
