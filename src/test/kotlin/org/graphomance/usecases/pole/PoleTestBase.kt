package org.graphomance.usecases.pole

import org.graphomance.api.DbType
import org.graphomance.engine.GraphomanceTest

@GraphomanceTest(customDatabaseName = "pole", dbTargets = [DbType.NEO4J, DbType.MEMGRAPH])
abstract class PoleTestBase
