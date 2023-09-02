package org.graphomance.reporting

import java.io.File
import java.util.Locale
import org.graphomance.api.DbType
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import org.jetbrains.kotlinx.dataframe.api.emptyDataFrame
import org.jetbrains.kotlinx.dataframe.api.into
import org.jetbrains.kotlinx.dataframe.api.last
import org.jetbrains.kotlinx.dataframe.api.rename
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.io.read
import org.jetbrains.kotlinx.dataframe.io.writeCSV

@DataSchema
class TestResult(
    val testName: String,
    var neo4jP95: Double? = null,
    var memGraphP95: Double? = null,
)

class ChartsGenerator {

    private val providers = arrayOf(DbType.NEO4J, DbType.MEMGRAPH)

    fun run() {
        println("Generating master results file")

        val result = mutableMapOf<String, TestResult>()

        providers.forEach { dbType ->
            val dir = "./results/${dbType.name.lowercase(Locale.getDefault())}"
            val path = File(dir)
            path.walk().filter { it.name.endsWith(".csv") }.forEach { file ->
                val testName = file.name.removeSuffix(".csv")
                val df = DataFrame.read(file.absoluteFile)
                df.last()["p95"]?.toString()?.toDouble()?.let { p95 ->
                    val testResult = result.getOrPut(testName) { TestResult(testName) }
                    when (dbType) {
                        DbType.NEO4J -> testResult.neo4jP95 = p95
                        DbType.MEMGRAPH -> testResult.memGraphP95 = p95
                        else -> Unit
                    }
                }
            }
        }

        val finalDf = result.values.sortedBy { it.testName }.asIterable().toDataFrame()
            .rename { all() }.into("Test Name", "Neo4J p(95) [ms]", "Memgraph p(95) [ms]")
        val outFileName = "./results/master-results.csv"
        println("Writing to file $outFileName")
        finalDf.writeCSV(outFileName)

        println("Done")
    }
}


fun main() {
    ChartsGenerator().run()
}