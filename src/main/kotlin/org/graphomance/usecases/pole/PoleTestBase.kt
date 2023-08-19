package org.graphomance.usecases.pole

import org.graphomance.usecases.TestBase

abstract class PoleTestBase : TestBase() {
    override fun skipFor() = arrayOf(org.graphomance.api.DbType.ARANGODB)

    override fun databaseName() = "pole"
}