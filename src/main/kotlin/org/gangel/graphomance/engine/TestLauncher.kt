package org.gangel.graphomance.engine

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.CsvReporter
import com.codahale.metrics.MetricAttribute
import com.codahale.metrics.SharedMetricRegistries
import org.apache.commons.cli.*
import org.apache.commons.lang3.time.StopWatch
import org.gangel.graphomance.api.*
import org.gangel.graphomance.metrics.Metrics
import org.gangel.graphomance.metrics.TimeGauge
import org.gangel.graphomance.usecases.CreateBasicRelationTest
import org.gangel.graphomance.usecases.TestBase
import org.gangel.graphomance.usecases.node.CreateSingleVertex
import org.gangel.graphomance.usecases.pole.CrimeTotals
import org.gangel.graphomance.usecases.relation.CreateRelationsInFlatStructure
import org.gangel.graphomance.usecases.relation.CreateRelationsInStarStructure
import org.gangel.graphomance.vendor.arangodb.ArangoConnectionProducer
import org.gangel.graphomance.vendor.arangodb.ArangoConnectionSettings
import org.gangel.graphomance.vendor.arangodb.ArangoSessionProducer
import org.gangel.graphomance.vendor.neo4j.NeoConnectionProducer
import org.gangel.graphomance.vendor.neo4j.NeoConnectionSettings
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

object TestLauncher {
	private const val BREAK_LINE = "\n-------------------------------------------------------------------\n"

	@JvmStatic
	fun main(args: Array<String>) {
		val opts = Options()
		opts.addOption(Option.builder("p")
						   .longOpt("provider")
						   .required()
						   .hasArg()
						   .build())
		opts.addOption(Option.builder("h")
						   .longOpt("host")
						   .required()
						   .hasArg()
						   .build())
		opts.addOption(Option.builder("d")
						   .longOpt("dbname")
						   .required(false)
						   .hasArg()
						   .build())
		opts.addOption(Option.builder("t")
			.longOpt("tests")
			.required(false)
			.hasArg()
			.required(false).build())
		val parser: CommandLineParser = DefaultParser()
		val line: CommandLine = try {
			parser.parse(opts, args)
		} catch (e: ParseException) {
			System.err.println(e.message)
			val formatter = HelpFormatter()
			formatter.printHelp("Graphomance", opts)
			return
		}
		Metrics.init()
		val connection: Connection
		val sessionProducer: SessionProducer
		val dbUrlOrPath = line.getOptionValue("h")
		val dbName = line.getOptionValue("d")
		val dbKind = line.getOptionValue("p")
		val testNames = line.getOptionValue("t")?.split(",")?.toSet()
		val dbType: DbType = DbType.of(dbKind)
		when (dbType) {
			DbType.NEO4J -> {
				val connProducer = NeoConnectionProducer()
				val connSetup = NeoConnectionSettings(dbPath = dbUrlOrPath)
				connection = connProducer.connect(connSetup)
				sessionProducer = connProducer
			}
			DbType.ARANGODB -> {
				Objects.requireNonNull(dbName, "Database name for ArangoDB is required!")
				val connProducer: ConnectionProducer = ArangoConnectionProducer()
				val connSettings = ArangoConnectionSettings(dbName = dbName,
					user = "root",
					password = "admin")
				connection = connProducer.connect(connSettings)
				sessionProducer = ArangoSessionProducer()
			}
			else -> {
				System.err.println("Unsupported database type")
				return
			}
		}
		val csvReporter = CsvReporter.forRegistry(SharedMetricRegistries.getDefault())
			.convertRatesTo(TimeUnit.SECONDS)
			.convertDurationsTo(TimeUnit.MICROSECONDS)
			.formatFor(Locale.US)
			.build(File("."))
		val reporter = ConsoleReporter.forRegistry(SharedMetricRegistries.getDefault())
			.formattedFor(Locale.US)
			.disabledMetricAttributes(setOf(MetricAttribute.STDDEV,
											 MetricAttribute.P50,
											 MetricAttribute.P95,
											 MetricAttribute.P99,
											 MetricAttribute.MAX,
											 MetricAttribute.M1_RATE,
											 MetricAttribute.M5_RATE,
											 MetricAttribute.M15_RATE))
			.convertRatesTo(TimeUnit.SECONDS)
			.convertDurationsTo(TimeUnit.MICROSECONDS)
			.build()
		val allTests = getFilteredTestObjects(testNames)
		System.out.printf("Starting with database: %s\n", dbType.toString())
		allTests.forEach(Consumer { test: TestCase ->
			System.out.printf(BREAK_LINE)
			if (test.skipFor(dbType)) {
				System.out.printf("Skipping test '%s'\n",
								  test.javaClass.simpleName)
			} else {
				runTest(test, connection, sessionProducer)
			}
		})
		connection.close()
		reporter.report()
		csvReporter.report()
	}

	private fun addNewMetric(testName: String, metricsName: String?, body: Runnable) {
		val t = TimeGauge()
		SharedMetricRegistries.getDefault()
			.register(String.format("%s.%s", testName, metricsName), t)
		t.start()
		body.run()
		t.stop()
	}

	private fun runTest(test: TestCase, connection: Connection, sessionProducer: SessionProducer) {
		val createNewSession: ()->Session = { sessionProducer.createSession(connection) }
		try {
			val testName = test.javaClass.simpleName
			System.out.printf("Setup test: %s \n", testName)
			test.setUpTest(createNewSession())
			println("Cleaning up...")
			test.cleanUpData(createNewSession())
			System.out.printf("Test data generation...\n")
			addNewMetric(testName, "DataGeneration") {
				test.createTestData(createNewSession())
			}
			val timer = StopWatch.createStarted()
			// TODO: design different approach for stages
			val stages = (test as? TestBase)?.stages ?: mapOf("test" to { session -> test.performTest(session) })
			var stageNum = 0
			stages.forEach { (stageName, action) ->
				++stageNum
				System.out.printf(" > running test stage %d - %s..\n", stageNum, stageName)
				addNewMetric(testName, stageName) { action(createNewSession()) }
			}
			timer.stop()
			println("Test done. Time: $timer")
			println("Cleaning up...")
			addNewMetric(testName, "CleanUp") { test.cleanUpData(createNewSession()) }
			println("Test done.")
		} catch (e: Throwable) {
			println("Test error: " + e.message)
			e.printStackTrace()
		}
	}

	private fun findTestClasses(): List<Class<out TestBase>> {
		val packagePath = "org.gangel.graphomance"
		val reflections =
			Reflections(
				ConfigurationBuilder()
					.filterInputsBy(FilterBuilder().includePackage(packagePath))
					.setUrls(ClasspathHelper.forPackage(packagePath))
					.setScanners(SubTypesScanner(false))
			)
		val typeList = reflections.getSubTypesOf(TestBase::class.java)
		return typeList.toList()
	}

	private fun getFilteredTestObjects(testNames: Set<String>?): List<TestCase> {
		val allClasses = findTestClasses()
		val filteredClasses =  if (testNames == null) {
			allClasses
		} else {
			allClasses.filter { testNames.contains(it.simpleName) }
		}
		val testObjects = filteredClasses.mapNotNull { it.newInstance() }
		testObjects.forEach {
			println("Test ${it::class.simpleName}")
		}
		return testObjects;
	}
}