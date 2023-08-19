package org.graphomance.usecases.node

import com.codahale.metrics.SharedMetricRegistries
import com.codahale.metrics.Timer
import java.time.Duration
import org.graphomance.api.IndexType
import org.graphomance.api.Session
import org.graphomance.engine.TestLimit
import org.graphomance.usecases.TestBase

abstract class CreateSingleVertexBase protected constructor(protected val timerName: String, protected val indexType: IndexType?, protected val isIndexLong: Boolean, protected val isIndexString: Boolean) : TestBase() {
    private val MIN_ITERATIONS: Long = 100000
    private val MIN_TEST_TIME = Duration.ofSeconds(5)
    private val MAX_TEST_TIME = Duration.ofSeconds(30)
    override fun setUpTest(session: Session) {}
    override fun createTestData(session: Session) {
        if (session.schemaApi().classExists(USER_CLASS)) {
            session.objectApi().deleteAllNodes(USER_CLASS)
        }
        session.schemaApi().createClass(USER_CLASS)
//        if (isIndexString && isIndexLong) {
//            if (indexType != null) {
//                session.schemaApi().createIndex("User_uid_login", USER_CLASS, indexType, true, "uid, login")
//            }
//        } else {
//            if (isIndexString && indexType != null) {
//                session.schemaApi().createIndex("User_login", USER_CLASS, indexType, true, "login")
//            }
//            if (isIndexLong && indexType != null) {
//                session.schemaApi().createIndex("User_uid", USER_CLASS, indexType, true, "uid")
//            }
//        }
    }

    override fun performTest(session: Session) {
        val limit = TestLimit(MIN_ITERATIONS, MIN_TEST_TIME, MAX_TEST_TIME)
        val timerMetric = Timer()
        SharedMetricRegistries.getDefault().register(timerName, timerMetric)
        val props: MutableMap<String, Any> = HashMap()
        var cnt: Long = 0
        while (!limit.isDone) {
            ++cnt
            props["login"] = "name_$cnt"
            props["uid"] = cnt
            timerMetric.time().use { timer ->
                        session.objectApi().createNode(USER_CLASS, props)
                    }
            limit.increment()
        }
        val cntObjects = session.schemaApi().countObjects(USER_CLASS)
        System.out.printf("End of operations. Iterations: %d, objects: %d\n", cnt, cntObjects)
    }

    override fun cleanUpData(session: Session) {
//        session.schemaApi().dropAllIndexesOnClass(USER_CLASS)
        session.objectApi().deleteAllNodes(USER_CLASS)
        session.schemaApi().dropClass(USER_CLASS)
    }

    companion object {
        private const val USER_CLASS = "UserNode"
    }

}