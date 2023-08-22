package org.graphomance.api

enum class DbType {
	UNKNOWN, NEO4J, ARANGODB, MEMGRAPH;

	companion object {
		fun of(s: String): DbType =valueOf(s.toUpperCase())
	}
}