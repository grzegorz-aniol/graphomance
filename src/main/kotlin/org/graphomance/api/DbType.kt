package org.graphomance.api

import java.util.Locale

enum class DbType {
	UNKNOWN, NEO4J, ARANGODB, MEMGRAPH;

	companion object {
		fun of(s: String): DbType =valueOf(s.uppercase(Locale.getDefault()))
	}
}