package org.gangel.graphomance

enum class DbType {
	NEO4J, ARANGODB;

	companion object {
		fun of(s: String): DbType {
			return valueOf(s.toUpperCase())
		}
	}
}