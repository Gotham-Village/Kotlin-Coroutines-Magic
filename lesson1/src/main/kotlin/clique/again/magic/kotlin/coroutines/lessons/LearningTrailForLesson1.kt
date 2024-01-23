package clique.again.magic.kotlin.coroutines.lessons

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

private val globalLogger by lazy { KotlinLogging.logger {} }

/**
 * Uncomment lines in various orders to see what happens.
 */
fun main() {

    val myLesson1 = Lesson1()
    myLesson1.task1()

    globalLogger experiment1 myLesson1          // Experiment 1 - will it run? - what happens here?

//    What happens if you make app wait, like so?
//    val x = readln()

//    globalLogger experiment2 myApp          // Experiment 2 - will it run? - what happens here?
}

infix fun KLogger.experiment1(app: Lesson1) {
    entry("Experiment 1 - will it run?")
    val job = app.runTask2()
    info { "Experiment 1 Job is: $job" }
    exit(job)
}

@Suppress("unused")
infix fun KLogger.experiment2(app: Lesson1) {
    entry("Experiment 2 - will it run?")
    val bigJob = app.runTask3()
    info { "Experiment 2 Job is: $bigJob" }
    info { "What about Experiment 1 output?" }
    exit(bigJob)
}