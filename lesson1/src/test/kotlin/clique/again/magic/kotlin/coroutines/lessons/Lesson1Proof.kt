package clique.again.magic.kotlin.coroutines.lessons

import kotlin.test.Test
import kotlin.test.assertNotNull

class Lesson1Proof {

    @Test fun appHasAGreeting() {
        val classUnderTest = Lesson1()
        assertNotNull(classUnderTest.greeting, "demo should have a greeting")
    }
}
