import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.slf4j.LoggerFactory

val useJavaVer: String by project

val kotlinCoroutinesVersion: String by project

val kotlinLoggingVersion: String by project
val slf4jVersion: String by project
val logbackClassicVersion: String by project

val jupiterVersion: String by project
val kotestVersion: String by project

private val log by lazy { LoggerFactory.getLogger(this::class.java) }


with(project) {
    log.warn(
        """
    
    Configuring Lesson 1 Module;
    Group:      $group
    Name:       $name
    Version:    $version
    
    """.trimIndent()
    )
}



plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(useJavaVer)
    }
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$kotlinCoroutinesVersion"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")


    api("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("io.github.oshai:kotlin-logging:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")

    testImplementation(platform("org.junit:junit-bom:$jupiterVersion"))
    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-property")

    testImplementation("org.logcapture:logcapture-core:1.3.2")
    testImplementation("org.logcapture:logcapture-kotest:1.3.2")

    testImplementation("io.kotest:kotest-extensions-htmlreporter")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        showStackTraces = true
        exceptionFormat = FULL
        events = setOf(STARTED, PASSED, SKIPPED, FAILED, STANDARD_OUT, STANDARD_ERROR)
    }
    systemProperty("kotest.framework.dump.config", true)
    systemProperty("gradle.build.dir", layout.buildDirectory)
    systemProperties = System.getProperties().asIterable().associate { it.key.toString() to it.value }

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = useJavaVer
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

application {
    mainClass.set("${group}.demo.AppKt")
}

