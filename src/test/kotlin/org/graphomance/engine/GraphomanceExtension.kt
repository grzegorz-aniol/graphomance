package org.graphomance.engine

import com.codahale.metrics.Timer
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
import org.junit.jupiter.api.extension.ConditionEvaluationResult
import org.junit.jupiter.api.extension.ExecutionCondition
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class GraphomanceExtension : BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
    AfterEachCallback, AfterTestExecutionCallback, AfterAllCallback, ParameterResolver, ExecutionCondition {

    private var dbName: String?
    private var hostUrl: String
    private var dbType: DbType
    private lateinit var connection: Connection
    private lateinit var sessionProducer: SessionProducer
    private val namespace = ExtensionContext.Namespace.create(GraphomanceExtension::class.java.name)

    init {
        dbType = DbType.of(requireNotNull(getParameters("DB_TYPE")) { "Missing DB_TYPE parameter" })
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
        val testCtx = getOrCreateTestExecutionContext(context)
        testCtx.start()
    }

    override fun afterTestExecution(context: ExtensionContext) {
        val endTime = System.nanoTime()
        val testCtx = getOrCreateTestExecutionContext(context)
        if (testCtx.isStarted()) {
            testCtx.stop(endTime)
        }
        if (!testCtx.isCompleted()) {
            throw RuntimeException("Test timer has not been started")
        }
        // With @RepeatedTest, we can measure exactly how many times time is measured
        // otherwise, when TestTimer object is use to capture metrics, we may expect more results
        // TODO: better handle case with @RepeatedTest and TestTimer used together
        val lastIteration = (testCtx.getExpectedIterations() == testCtx.getCount())
        if (lastIteration) {
            val totalTimeInSec = 1e-9 * testCtx.getTotalTimeNs()
            val avgTimeInMs = (1e-6 * testCtx.getTotalTimeNs()) / testCtx.getCount()
            println("Test: '${context.testMethod.get().name}', iterations: ${testCtx.getCount()}, total time: $totalTimeInSec [s], avg time: $avgTimeInMs [ms]")
        }
    }

    override fun afterEach(context: ExtensionContext) {
        // NOP
    }

    override fun supportsParameter(parameterContext: ParameterContext, context: ExtensionContext): Boolean {
        val type = parameterContext.parameter.type
        return type == Session::class.java || type == TestTimer::class.java

    }

    override fun resolveParameter(parameterContext: ParameterContext, context: ExtensionContext): Any {
        return when (parameterContext.parameter.type) {
            Session::class.java -> createSession()
            TestTimer::class.java -> produceTestTimer(context)
            else -> throw RuntimeException("Cannot instantiate test parameter of class ${parameterContext.javaClass.name}")
        }
    }

    private fun produceTestTimer(context: ExtensionContext): TestTimer {
        if (context.testMethod.isEmpty) {
            throw RuntimeException("TestTimer can be used only within a test method")
        }
        val testCtx = getOrCreateTestExecutionContext(context)
        return object : TestTimer {
            override fun <T> timeMeasureWithResult(actionWithResult: () -> T): T {
                testCtx.start()
                try {
                    return actionWithResult()
                } finally {
                    testCtx.stop()
                }
            }
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        MetricsService.reportMetrics()
    }

    override fun evaluateExecutionCondition(context: ExtensionContext): ConditionEvaluationResult {
        val cls = context.requiredTestClass
        val annotation = findAnnotation(cls, GraphomanceTest::class.java)
        val testForDbProvider = annotation?.dbTargets?.toSet() ?: emptySet()
        return if (testForDbProvider.contains(dbType)) {
            ConditionEvaluationResult.enabled("Supported database type")
        } else {
            ConditionEvaluationResult.disabled("Unsupported database type")
        }
    }


    private fun getOrCreateTestExecutionContext(context: ExtensionContext) : TestExecutionContext {
        val store = getParentStore(context)
        val testClass = context.requiredTestClass
        val testMethod = context.requiredTestMethod
        var testCtx = store.get(testMethod) as? TestExecutionContext
        if (testCtx == null) {
            val repeatedTestAnnotation: RepeatedTest? = context.element
                .map { it.annotations.filterIsInstance(RepeatedTest::class.java).firstOrNull() }
                .orElse(null)
            testCtx = TestExecutionContext(metric = MetricsService.registerTimeGaugeMetric(testClass, testMethod.name), expectedIterations = repeatedTestAnnotation?.value ?: 1)
            store.put(testMethod, testCtx)
        }
        return testCtx
    }

    private fun createSession() = sessionProducer.createSession(connection)

    private fun setupDbConnectionProvider(customDatabaseName: String?) {
        when (dbType) {
            DbType.NEO4J, DbType.MEMGRAPH -> {
                val connProducer = NeoConnectionProducer(dbType)
                // using custom database name only with Neo4j Enterprise
                val dbName = (customDatabaseName ?: dbName ?: "neo4j").takeIf { dbType == DbType.NEO4J }
                val connSetup = NeoConnectionSettings(dbPath = hostUrl, dbName = dbName)
                connection = connProducer.connect(connSetup)
                sessionProducer = connProducer
            }

            DbType.ARANGODB -> {
                requireNotNull(dbName) { "Database name for ArangoDB is required!" }
                val connProducer: org.graphomance.api.ConnectionProducer = ArangoConnectionProducer()
                val connSettings = ArangoConnectionSettings(
                    host = hostUrl.split(':')[0],
                    port = hostUrl.split(':')[1].toInt(),
                    dbName = dbName!!,
                    user = "root",
                    password = "password"
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

    private class TestExecutionContext(private val metric: Timer, private var expectedIterations: Int = 1) {
        private var startTimeNs: Long = 0
        private var totalTimeNs: Long = 0
        private var count = 0
        private var isStarted = false
        private var isCompleted = false
        fun start() {
            isStarted = true
            isCompleted = false
            startTimeNs = System.nanoTime()
            totalTimeNs = startTimeNs
        }
        fun stop(stopTimeNs: Long = System.nanoTime()) {
            totalTimeNs = stopTimeNs - startTimeNs
            metric.update(totalTimeNs, TimeUnit.NANOSECONDS)
            count += 1
            isStarted = false
            isCompleted = true
            startTimeNs = 0L
        }

        fun isStarted() = isStarted
        fun isCompleted() = isCompleted
        fun getTotalTimeNs() : Long = totalTimeNs
        fun getCount() = count
        fun getExpectedIterations() = expectedIterations
    }

    private fun getParentStore(ctx: ExtensionContext) = ctx.parent.get().getStore(namespace)

}