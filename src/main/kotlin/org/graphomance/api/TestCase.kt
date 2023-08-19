package org.graphomance.api

interface TestCase {
	fun setUpTest(session: Session) = Unit
	fun createTestData(session: Session) = Unit
	fun performTest(session: Session)
	fun cleanUpData(session: Session) = Unit
	fun skipFor(): Array<org.graphomance.api.DbType>? = null
	fun skipFor(dbType: org.graphomance.api.DbType): Boolean {
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