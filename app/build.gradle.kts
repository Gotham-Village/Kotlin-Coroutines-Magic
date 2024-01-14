import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.qodana.tasks.QodanaScanTask
import org.slf4j.LoggerFactory

val useJavaVer: String by project

val kotlinxDateTimeVersion: String by project

val kotlinLoggingVersion: String by project
val slf4jVersion: String by project
val logbackClassicVersion: String by project

val jupiterVersion: String by project
val kotestVersion: String by project
val assertjVersion: String by project

val jacocoToolVersion: String by project

val repoKotlinDev: String by project

val group: String by project
val version: String by project

private val log by lazy { LoggerFactory.getLogger(group) }

log.warn(
    """
    
    Kotlin Coroutines Magic build started.
    ${group}::${version}
    
    """.trimIndent()
)


plugins {

    kotlin("jvm")
    id("org.jetbrains.qodana")
    id("org.jetbrains.dokka")
    jacoco

    application

    id("org.asciidoctor.jvm.pdf")
    id("org.asciidoctor.jvm.gems")
    id("org.asciidoctor.jvm.epub")
    id("org.asciidoctor.jvm.convert")
}

repositories {
    mavenCentral()
    maven(repoKotlinDev)
    google()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(useJavaVer)
    }
}

dependencies {
    implementation(platform(kotlin("bom")))
    testImplementation(platform("org.junit:junit-bom:$jupiterVersion"))
    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation(platform("org.assertj:assertj-bom:$assertjVersion"))

    api("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("io.github.oshai:kotlin-logging:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")

    testImplementation(kotlin("test"))

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-property")

    testImplementation("io.kotest:kotest-extensions-junitxml")
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

    finalizedBy(tasks.jacocoTestReport)

    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = useJavaVer
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

tasks.withType<QodanaScanTask> {
    dependsOn(tasks.test, tasks.jacocoTestReport, tasks.distTar, tasks.distZip)
    mustRunAfter(tasks.test, tasks.jacocoTestReport, tasks.distTar, tasks.distZip)
}

jacoco {
    toolVersion = jacocoToolVersion
    reportsDirectory.set(layout.projectDirectory.file(".qodana/code-coverage/result.xml").asFile)
}

tasks.withType<JacocoReport>{
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.withType<JacocoCoverageVerification>{
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }
        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("${group}.*")
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                minimum = "0.4".toBigDecimal()

            }
        }
    }
}
application {
    mainClass.set("${group}.demo.AppKt")
}

