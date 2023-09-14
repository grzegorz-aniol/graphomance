package org.graphomance.usecases.fraud

import org.graphomance.api.DbType
import org.graphomance.engine.GraphomanceTest

@GraphomanceTest(customDatabaseName = "fraud", dbTargets = [DbType.NEO4J, DbType.MEMGRAPH])
abstract class FraudTestBase
