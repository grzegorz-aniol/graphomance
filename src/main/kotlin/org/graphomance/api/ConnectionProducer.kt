package org.graphomance.api

interface ConnectionProducer {
    fun connect(settings: ConnectionSettings): Connection
}