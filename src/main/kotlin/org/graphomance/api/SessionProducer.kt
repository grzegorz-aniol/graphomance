package org.graphomance.api

interface SessionProducer {
	fun createSession(connection: org.graphomance.api.Connection): Session
}