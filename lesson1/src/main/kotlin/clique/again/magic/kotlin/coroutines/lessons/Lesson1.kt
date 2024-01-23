@file:OptIn(DelicateCoroutinesApi::class)

package clique.again.magic.kotlin.coroutines.lessons

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*

class Lesson1 {

    internal val logger by lazy { KotlinLogging.logger {} }

    /**
     * A blocking value returned from a function.
     */
    val greeting: String
        get() {
            return "Hello World!"
        }

    /**
     * Stevedza blocking Task 1.
     */
    fun task1() {
        logger.info { "Task 1: $greeting" }
    }


    /**
     * Stevedza blocking Task 2.
     */
    internal suspend fun task2(i: Int = 0): String {
        delay((i) * 400 + 100L)                                // This necessitates the `suspend` - but why?
        return ("Task 2 call number $i: $greeting").apply { logger.info { this } }
    }

    internal suspend fun task3() = coroutineScope {
        launch {
            (2..5).map { task2(it) }
        }
    }

    /**
     * Task 2 is run in some other global scope.
     */
    fun runTask2(): Job {
        // Returning the created job here to inspect it later in experiments.
        return GlobalScope.launch {
            task2(-1)
        }
    }

    /**
     * Task 3 is blocking…
     * What if tasks two and three runners logic is swapped?
     */
    fun runTask3() = runBlocking {
        task3()
    }
}


