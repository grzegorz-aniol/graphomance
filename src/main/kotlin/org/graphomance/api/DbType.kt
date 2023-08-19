package org.graphomance.api

enum class DbType {
	NEO4J, ARANGODB, MEMGRAPH;

	companion object {
		fun of(s: String): org.graphomance.api.DbType {
			return valueOf(s.toUpperCase())
		}
	}
}