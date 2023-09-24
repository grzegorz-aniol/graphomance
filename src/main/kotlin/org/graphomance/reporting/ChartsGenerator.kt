package org.graphomance.reporting

import io.github.cdimascio.dotenv.Dotenv
import java.io.File
import java.util.Locale
import org.graphomance.api.DbType
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import org.jetbrains.kotlinx.dataframe.api.last
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.io.read
import org.jetbrains.kotlinx.dataframe.io.writeCSV

@DataSchema
class TestResult(
    val testName: String,
    var neo4jCount: Long? = null,
    var neo4jMin: Double? = null,
    var neo4jP50: Double? = null,
    var neo4jP95: Double? = null,
    var neo4jMax: Double? = null,
    var neo4jStdDev: Double? = null,
    var neo4jMean: Double? = null,
    var memgraphCount: Long? = null,
    var memgraphMin: Double? = null,
    var memgraphP50: Double? = null,
    var memgraphP95: Double? = null,
    var memgraphMax: Double? = null,
    var memgraphStdDev: Double? = null,
    var memgraphMean: Double? = null,
    var arangoDbCount: Long? = null,
    var arangoDbMin: Double? = null,
    var arangoDbP50: Double? = null,
    var arangoDbP95: Double? = null,
    var arangoDbMax: Double? = null,
    var arangoDbStdDev: Double? = null,
    var arangoDbMean: Double? = null,
)

class ChartsGenerator {

    private val providers = arrayOf(DbType.NEO4J, DbType.MEMGRAPH, DbType.ARANGODB)
    private val dotenv = Dotenv.load()

    fun run() {
        println("Generating master results file")

        val neo4jVersion = dotenv["NEO4J_VERSION"]
        val memgraphVersion = dotenv["MEMGRAPH_VERSION"]
        val arangoDbVersion = dotenv["ARANGODB_VERSION"]
        val neo4jHeader = "Neo4j $neo4jVersion"
        val memgraphHeader = "Memgraph $memgraphVersion"
        val arangoDbHeader = "ArangoDB $arangoDbVersion"

        val result = mutableMapOf<String, TestResult>()

        providers.forEach { dbType ->
            val dir = "./results/${dbType.name.lowercase(Locale.getDefault())}"
            val path = File(dir)
            path.walk().filter { it.name.endsWith(".csv") }.forEach { file ->
                val testName = file.name.removeSuffix(".csv")
                val testResult = result.getOrPut(testName) { TestResult(testName) }
                val df = DataFrame.read(file.absoluteFile)
                val lastRunResult = df.last()
                val value = lastRunResult["p95"]

                when (dbType) {
                    DbType.NEO4J -> {
                        testResult.neo4jCount = lastRunResult["count"]?.getLongValue()
                        testResult.neo4jMin = lastRunResult["min"]?.getDoubleValue()
                        testResult.neo4jP50 = lastRunResult["p50"]?.getDoubleValue()
                        testResult.neo4jP95 = lastRunResult["p95"]?.getDoubleValue()
                        testResult.neo4jMax = lastRunResult["max"]?.getDoubleValue()
                        testResult.neo4jStdDev = lastRunResult["stddev"]?.getDoubleValue()
                        testResult.neo4jMean = lastRunResult["mean"]?.getDoubleValue()
                    }
                    DbType.MEMGRAPH -> {
                        testResult.memgraphCount = lastRunResult["count"]?.getLongValue()
                        testResult.memgraphMin = lastRunResult["min"]?.getDoubleValue()
                        testResult.memgraphP50 = lastRunResult["p50"]?.getDoubleValue()
                        testResult.memgraphP95 = lastRunResult["p95"]?.getDoubleValue()
                        testResult.memgraphMax = lastRunResult["max"]?.getDoubleValue()
                        testResult.memgraphStdDev = lastRunResult["stddev"]?.getDoubleValue()
                        testResult.memgraphMean = lastRunResult["mean"]?.getDoubleValue()
                    }
                    DbType.ARANGODB -> {
                        testResult.arangoDbCount = lastRunResult["count"]?.getLongValue()
                        testResult.arangoDbMin = lastRunResult["min"]?.getDoubleValue()
                        testResult.arangoDbP50 = lastRunResult["p50"]?.getDoubleValue()
                        testResult.arangoDbP95 = lastRunResult["p95"]?.getDoubleValue()
                        testResult.arangoDbMax = lastRunResult["max"]?.getDoubleValue()
                        testResult.arangoDbStdDev = lastRunResult["stddev"]?.getDoubleValue()
                        testResult.arangoDbMean = lastRunResult["mean"]?.getDoubleValue()
                    }
                    else -> Unit
                }
            }
        }

        val finalDf = result.values.sortedBy { it.testName }.asIterable().toDataFrame()
        val outFileName = "./results/master-results.csv"
        println("Writing to file $outFileName")
        finalDf.writeCSV(outFileName)

        println("Done")
    }

    private fun Any?.getDoubleValue(): Double? = this?.toString()?.toDouble()
    private fun Any?.getLongValue(): Long? = this?.toString()?.toLong()
}


fun main() {
    ChartsGenerator().run()
}