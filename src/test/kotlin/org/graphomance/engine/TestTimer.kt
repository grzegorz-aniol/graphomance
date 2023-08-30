package org.graphomance.engine

interface TestTimer {

    fun timeMeasure(action: Runnable) = timeMeasureWithResult { action.run() }

    fun <T> timeMeasureWithResult(actionWithResult: ()->T): T

}
