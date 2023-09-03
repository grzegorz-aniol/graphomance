package org.graphomance.vendor.arangodb

import com.arangodb.ArangoDB
import com.arangodb.ContentType
import com.arangodb.Protocol
import com.arangodb.serde.jackson.JacksonSerde
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

class ArangoConnectionProducer : org.graphomance.api.ConnectionProducer {
    override fun connect(settings: org.graphomance.api.ConnectionSettings): org.graphomance.api.Connection {
        val connSettings = settings as? ArangoConnectionSettings ?: throw RuntimeException("Required ArangoDB")
        val arangoDb = ArangoDB.Builder()
            .host(settings.host, settings.port)
            .user(connSettings.user)
            .password(connSettings.password)
            .serde(JacksonSerde.of(ContentType.JSON).configure { mapper ->
                mapper.registerModules(KotlinModule.Builder().build(), JavaTimeModule())
            }).build()
        return ArangoConnection(db = arangoDb, connectionSettings = connSettings)
    }
}