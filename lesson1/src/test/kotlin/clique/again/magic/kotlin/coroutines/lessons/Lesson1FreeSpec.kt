@file:OptIn(ExperimentalCoroutinesApi::class)

package clique.again.magic.kotlin.coroutines.lessons

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.logcapture.assertion.ExpectedLoggingMessage.aLog
import org.logcapture.kotest.LogCaptureListener

private val testLogger by lazy { KotlinLogging.logger {} }

/**
 * This is an example of test-driven tinkering that's expected for exploration.
 */
@DelicateCoroutinesApi
class Lesson1FreeSpec : FreeSpec({


    val mainThreadSurrogate = newSingleThreadContext("UI thread")

    beforeTest {
        testLogger.info { "Setting up coroutine context for ${it.name.testName} on ${mainThreadSurrogate.key}." }
        Dispatchers.setMain(mainThreadSurrogate)
    }

    afterTest { (testCase, result) ->
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        testLogger.info { "Dumping ${testCase.name.testName} coroutine context on ${mainThreadSurrogate.key} executed with $result." }
    }

    "greeting - is a static getter..." - {

        "a blocking getter" {
            Lesson1().greeting shouldStartWith "Hello World!"
        }
    }

    "task1" - {
        with(LogCaptureListener().also(::listener)) {
            "verify task 1 log" {
                Lesson1().task1()
                logged((aLog().info().withMessage("Task 1: Hello World!")))
            }
        }

    }

    "task2" { runTest { Lesson1().task2(-1) shouldBe "Task 2 call number -1: Hello World!" } }

    "task3" { runTest { Lesson1().task3().isCompleted shouldBe true } }
})
