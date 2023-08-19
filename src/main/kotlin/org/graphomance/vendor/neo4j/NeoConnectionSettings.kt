package org.graphomance.vendor.neo4j

class NeoConnectionSettings(val dbPath: String, val dbName: String? = null) : org.graphomance.api.ConnectionSettings