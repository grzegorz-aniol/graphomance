package org.graphomance.engine

import org.graphomance.api.DbType

annotation class GraphQuery(val dbType: DbType = DbType.UNKNOWN)
