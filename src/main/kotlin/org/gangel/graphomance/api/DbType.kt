package org.gangel.graphomance.api

enum class DbType {
	NEO4J, ARANGODB, MEMGRAPH;

	companion object {
		fun of(s: String): DbType {
			return valueOf(s.toUpperCase())
		}
	}
}