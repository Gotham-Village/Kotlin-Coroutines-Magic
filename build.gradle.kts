import org.slf4j.LoggerFactory


private val log by lazy { LoggerFactory.getLogger(this::class.java) }

with(project) {
    log.warn(
        """
    
    Configuring Kotlin Coroutines Magic Root Project Build File;
    Group:      $group
    Name:       $name
    Version:    $version
    
    """.trimIndent()
    )
}


plugins {
    kotlin("jvm") apply false
}
