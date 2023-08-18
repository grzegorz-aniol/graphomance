package org.gangel.graphomance.usecases.pole

import org.gangel.graphomance.api.DbType
import org.gangel.graphomance.usecases.TestBase

abstract class PoleTestBase : TestBase() {
    override fun skipFor() = arrayOf(DbType.ARANGODB)

    override fun databaseName() = "pole"
}