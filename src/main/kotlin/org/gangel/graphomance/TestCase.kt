package org.gangel.graphomance

interface TestCase {
	fun setUpTest(session: Session)
	fun createTestData(session: Session)
	fun performTest(session: Session)
	fun cleanUpData(session: Session)
	fun skipFor(): Array<DbType>?
	fun skipFor(dbType: DbType): Boolean {
		val skipForTypes = skipFor() ?: return false
		for (t in skipForTypes) {
			if (t == dbType) {
				return true
			}
		}
		return false
	}
}