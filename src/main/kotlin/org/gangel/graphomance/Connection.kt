package org.gangel.graphomance

import java.io.Closeable

interface Connection : Closeable, AutoCloseable {
	fun open()
	override fun close()
}