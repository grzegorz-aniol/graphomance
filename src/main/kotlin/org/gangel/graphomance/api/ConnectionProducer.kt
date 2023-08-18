package org.gangel.graphomance.api

interface ConnectionProducer {
	fun connect(settings: ConnectionSettings): Connection
}