package org.gangel.graphomance

interface ConnectionProducer {
	fun connect(settings: ConnectionSettings): Connection
}