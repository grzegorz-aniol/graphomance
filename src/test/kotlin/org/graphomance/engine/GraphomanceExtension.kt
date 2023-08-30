package org.graphomance.engine

import com.codahale.metrics.Timer
import java.util.Objects
import java.util.concurrent.TimeUnit
import org.graphomance.api.Connection
import org.graphomance.api.DbType
import org.graphomance.api.Session
import org.graphomance.api.SessionProducer
import org.graphomance.vendor.arangodb.ArangoConnectionProducer
import org.graphomance.vendor.arangodb.ArangoConnectionSettings
import org.graphomance.vendor.arangodb.ArangoSessionProducer
import org.graphomance.vendor.neo4j.NeoConnectionProducer
import org.graphomance.vendor.neo4j.NeoConnectionSettings
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class GraphomanceExtension : BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
    AfterEachCallback, AfterTestExecutionCallback, AfterAllCallback, ParameterResolver {

    private var dbName: String?
    private var hostUrl: String
    private var dbType: String
    private lateinit var connection: Connection
    private lateinit var sessionProducer: SessionProducer
    private val namespace = ExtensionContext.Namespace.create(GraphomanceExtension::class.java.name)

    init {
        dbType = requireNotNull(getParameters("DB_TYPE")) { "Missing DB_TYPE parameter" }
        hostUrl = requireNotNull(getParameters("URL")) { "Required URL parameter" }
        dbName = getParameters("DB_NAME")
    }

    override fun beforeAll(context: ExtensionContext) {
        val cls = context.requiredTestClass
        val annotation = findAnnotation(cls, GraphomanceTest::class.java)

        val customDatabaseName = annotation?.customDatabaseName?.takeIf { it.isNotBlank() }
        setupDbConnectionProvider(customDatabaseName)
    }

    override fun beforeEach(context: ExtensionContext) {
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        val store = getParentStore(context)
        val testClass = context.requiredTestClass
        val testMethod = context.requiredTestMethod
        var testCtx = store.get(testMethod) as? TestExecutionContext
        if (testCtx == null) {
            val repeatedTestAnnotation: RepeatedTest? = context.element
                .map { it.annotations.filterIsInstance(RepeatedTest::class.java).firstOrNull() }
                .orElse(null)
            testCtx = TestExecutionContext(MetricsService.registerTimeGaugeMetric(testClass.simpleName, testMethod.name))
            if (repeatedTestAnnotation != null) {
                testCtx.expectedIterations = repeatedTestAnnotation.value
            }
            store.put(testMethod, testCtx)
        }
        testCtx.start()
    }

    override fun afterTestExecution(context: ExtensionContext) {
        val endTime = System.nanoTime()
        val store = getParentStore(context)
        val testCtx = store.get(context.requiredTestMethod) as TestExecutionContext
        testCtx.stop(endTime)

        val lastIteration = (testCtx.expectedIterations == testCtx.count)
        if (lastIteration) {
            val totalTimeInSec = 1e-9 * testCtx.totalTimeNs
            val avgTimeInMs = (1e-6 * testCtx.totalTimeNs) / testCtx.count
            println("Test: '${context.testMethod.get().name}', iterations: ${testCtx.count}, total time: $totalTimeInSec [s], avg time: $avgTimeInMs [ms]")
        }
    }

    override fun afterEach(context: ExtensionContext) {
        // NOP
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == Session::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        return when (parameterContext.parameter.type) {
            Session::class.java -> createSession()
            else -> throw RuntimeException("Cannot instantiate test parameter of class ${parameterContext.javaClass.name}")
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        MetricsService.reportMetrics()
    }

    private fun createSession() = sessionProducer.createSession(connection)

    private fun setupDbConnectionProvider(customDatabaseName: String?) {
        when (val dbType = DbType.of(dbType)) {
            DbType.NEO4J, DbType.MEMGRAPH -> {
                val connProducer = NeoConnectionProducer(dbType)
                val connSetup =
                    NeoConnectionSettings(dbPath = hostUrl, dbName = customDatabaseName ?: dbName ?: "neo4j")
                connection = connProducer.connect(connSetup)
                sessionProducer = connProducer
            }

            DbType.ARANGODB -> {
                Objects.requireNonNull(dbName, "Database name for ArangoDB is required!")
                val connProducer: org.graphomance.api.ConnectionProducer = ArangoConnectionProducer()
                val connSettings = ArangoConnectionSettings(
                    dbName = "dbName",
                    user = "root",
                    password = "admin"
                )
                connection = connProducer.connect(connSettings)
                sessionProducer = ArangoSessionProducer()
            }

            else -> throw RuntimeException("Unsupported database type")
        }
    }

    private fun <T> findAnnotation(cls: Class<*>, annotation: Class<T>): T? {
        val graphomanceTestAnnotation = cls.annotations.filterIsInstance(annotation).firstOrNull()
        if (graphomanceTestAnnotation != null) {
            return graphomanceTestAnnotation
        }
        val s = cls.superclass
        if (s != Nothing::class.java && s != Any::class.java) {
            return findAnnotation(s, annotation)
        }
        return null
    }

    private fun getParameters(key: String): String? {
        return System.getProperty(key)?.takeIf { it.isNotBlank() } ?: System.getenv(key)?.takeIf { it.isNotBlank() }
    }

    private class TestExecutionContext(val metric: Timer) {
        var startTimeNs: Long = 0
        var totalTimeNs: Long = 0
        var count = 0
        var expectedIterations: Int = 1
        fun start() {
            startTimeNs = System.nanoTime()
            totalTimeNs = startTimeNs
        }
        fun stop(stopTimeNs: Long) {
            totalTimeNs = stopTimeNs - startTimeNs
            metric.update(totalTimeNs, TimeUnit.NANOSECONDS)
            count += 1
        }
    }

    private fun getParentStore(ctx: ExtensionContext) = ctx.parent.get().getStore(namespace)

}