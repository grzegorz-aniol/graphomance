package org.gangel.graphomance.api

import java.io.Closeable

interface Connection : Closeable, AutoCloseable {
	fun open()
	override fun close()
}