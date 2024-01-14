pluginManagement {
    val repoKotlinDev: String by extra

    val kotlinVersion: String by extra
    val qodanaPluginVersion: String by extra
    val dokkaVersion: String by extra
    val toolchainsFoojayResolverVersion: String by extra

    val asciidoctorJvmVersion: String by extra

    repositories {
        gradlePluginPortal()
        maven(repoKotlinDev)
        mavenCentral()
        google()
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        jacoco

        id("org.jetbrains.qodana") version qodanaPluginVersion
        id("org.jetbrains.dokka") version dokkaVersion
        id("org.gradle.toolchains.foojay-resolver-convention") version toolchainsFoojayResolverVersion

        id("org.asciidoctor.jvm.pdf") version asciidoctorJvmVersion
        id("org.asciidoctor.jvm.gems") version asciidoctorJvmVersion
        id("org.asciidoctor.jvm.epub") version asciidoctorJvmVersion
        id("org.asciidoctor.jvm.convert") version asciidoctorJvmVersion
    }
}

rootProject.name = "Kotlin-Coroutines-Magic"
include("app")