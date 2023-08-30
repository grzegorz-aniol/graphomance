package org.graphomance.api

interface SessionProducer {
    fun createSession(connection: Connection): Session
}