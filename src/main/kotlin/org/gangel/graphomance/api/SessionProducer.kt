package org.gangel.graphomance.api

interface SessionProducer {
	fun createSession(connection: Connection): Session
}