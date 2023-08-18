package org.gangel.graphomance.api

interface TestCase {
	fun setUpTest(session: Session) = Unit
	fun createTestData(session: Session) = Unit
	fun performTest(session: Session)
	fun cleanUpData(session: Session) = Unit
	fun skipFor(): Array<DbType>? = null
	fun skipFor(dbType: DbType): Boolean {
		val skipForTypes = skipFor() ?: return false
		for (t in skipForTypes) {
			if (t == dbType) {
				return true
			}
		}
		return false
	}
	fun databaseName(): String? = null
}