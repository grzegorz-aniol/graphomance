package org.gangel.graphomance.api

import java.io.Closeable

interface Session : AutoCloseable, Closeable {
	override fun close()
	fun schemaApi(): SchemaApi
	fun objectApi(): ObjectApi
}