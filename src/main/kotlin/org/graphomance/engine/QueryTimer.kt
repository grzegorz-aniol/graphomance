package org.graphomance.engine

interface QueryTimer {

    fun timeMeasure(action: Runnable) = timeMeasureWithResult { action.run() }

    fun <T> timeMeasureWithResult(actionWithResult: ()->T): T

}
