package org.gangel.graphomance.engine

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.CsvReporter
import com.codahale.metrics.MetricAttribute
import com.codahale.metrics.SharedMetricRegistries
import org.apache.commons.cli.*
import org.apache.commons.lang3.time.StopWatch
import org.gangel.graphomance.*
import org.gangel.graphomance.arangodb.ArangoConnectionProducer
import org.gangel.graphomance.arangodb.ArangoConnectionSettings
import org.gangel.graphomance.arangodb.ArangoSessionProducer
import org.gangel.graphomance.metrics.Metrics
import org.gangel.graphomance.metrics.TimeGauge
import org.gangel.graphomance.neo4j.NeoConnectionProducer
import org.gangel.graphomance.neo4j.NeoConnectionSettings
import org.gangel.graphomance.usecases.CreateBasicRelationTest
import org.gangel.graphomance.usecases.TestBase
import org.gangel.graphomance.usecases.node.CreateSingleVertex
import org.gangel.graphomance.usecases.relation.CreateRelationsInFlatStructure
import org.gangel.graphomance.usecases.relation.CreateRelationsInStarStructure
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

object TestLauncher {
	const val BREAKE_LINE = "\n-------------------------------------------------------------------\n"

	@JvmStatic
	fun main(args: Array<String>) {
		val opts = Options()
		opts.addOption(Option.builder("t")
						   .longOpt("type")
						   .required()
						   .hasArg()
						   .build())
		opts.addOption(Option.builder("u")
						   .longOpt("dburl")
						   .required()
						   .hasArg()
						   .build())
		opts.addOption(Option.builder("n")
						   .longOpt("dbname")
						   .required(false)
						   .hasArg()
						   .build())
		val parser: CommandLineParser = DefaultParser()
		val line: CommandLine
		line = try {
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
		val dbUrlOrPath = line.getOptionValue("u")
		val dbName = line.getOptionValue("n")
		val dbKind = line.getOptionValue("t")
		val dbType: DbType = DbType.Companion.of(dbKind)
		if (dbType == null) {
			System.err.println("Unknown database type")
			return
		}
		if (dbType == DbType.NEO4J) {
			val connProducer = NeoConnectionProducer()
			val connSetup = NeoConnectionSettings(dbPath = dbUrlOrPath)
			connection = connProducer.connect(connSetup)
			sessionProducer = connProducer
		} else if (dbType == DbType.ARANGODB) {
			Objects.requireNonNull(dbName, "Database name for ArangoDB is required!")
			val connProducer: ConnectionProducer = ArangoConnectionProducer()
			val connSettings = ArangoConnectionSettings(dbName = dbName,
				user = "root",
				password = "admin")
			connection = connProducer.connect(connSettings)
			sessionProducer = ArangoSessionProducer()
		} else {
			System.err.println("Unsupported database type")
			return
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
											 MetricAttribute.P75,
											 MetricAttribute.P95,
											 MetricAttribute.P98,
											 MetricAttribute.P999,
											 MetricAttribute.M1_RATE,
											 MetricAttribute.M5_RATE,
											 MetricAttribute.M15_RATE))
			.convertRatesTo(TimeUnit.SECONDS)
			.convertDurationsTo(TimeUnit.MICROSECONDS)
			.build()
		val allTests = listOf(
			CreateBasicRelationTest(),
			CreateSingleVertex(),
			CreateRelationsInFlatStructure(),
			CreateRelationsInStarStructure()
		)
		System.out.printf("Starting with database: %s\n", dbType.toString())
		allTests.forEach(Consumer { test: TestBase ->
			System.out.printf(BREAKE_LINE)
			if (test.skipFor(dbType)) {
				System.out.printf("Skipping test '%s'\n",
								  test.javaClass.simpleName)
			} else {
				sessionProducer.createSession(connection).use { session ->
					runTest(test, session)
				}
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

	fun runTest(test: TestBase, session: Session) {
		try {
			val testName = test.javaClass.simpleName
			System.out.printf("Setup test: %s \n", testName)
			test.setUpTest(session)
			println("Cleaning up...")
			test.cleanUpData(session)
			System.out.printf("Test data generation...\n")
			addNewMetric(testName, "DataGeneration") { test.createTestData(session) }
			val timer = StopWatch.createStarted()
			val stages = test.stages
			var stageNum = 0
			stages.forEach { (stageName, action) ->
				++stageNum
				System.out.printf(" > running test stage %d - %s..\n", stageNum, stageName)
				addNewMetric(testName, stageName) { action(session) }
			}
			timer.stop()
			println("Test done. Time: $timer")
			println("Cleaning up...")
			addNewMetric(testName, "CleanUp") { test.cleanUpData(session) }
			println("Test done.")
		} catch (e: Throwable) {
			println("Test error: " + e.message)
			e.printStackTrace()
		}
	}
}