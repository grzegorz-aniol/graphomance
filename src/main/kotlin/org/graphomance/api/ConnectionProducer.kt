package org.graphomance.api

interface ConnectionProducer {
	fun connect(settings: org.graphomance.api.ConnectionSettings): org.graphomance.api.Connection
}