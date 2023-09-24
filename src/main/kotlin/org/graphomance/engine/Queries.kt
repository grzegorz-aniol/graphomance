package org.graphomance.engine

import org.graphomance.api.DbType

typealias DbByTypeQueries = Map<DbType, String?>

fun dbByTypeQueries(vararg queryPair: Pair<DbType, String?>): DbByTypeQueries = queryPair.toMap()

fun DbByTypeQueries.getQuery(dbType: DbType) = this[dbType] ?: throw RuntimeException("Unsupported database")

