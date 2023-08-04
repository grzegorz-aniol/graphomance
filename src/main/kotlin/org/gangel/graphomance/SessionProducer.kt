package org.gangel.graphomance

interface SessionProducer {
	fun createSession(connection: Connection): Session
}