pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        kotlin("multiplatform").version(extra["kotlin_version"] as String)
        kotlin("jvm").version(extra["kotlin_version"] as String)
        kotlin("plugin.serialization").version(extra["kotlin_version"] as String)
        id("org.jetbrains.compose").version(extra["compose_version"] as String)
        id("io.ktor.plugin").version(extra["ktor_version"] as String)
    }
}
rootProject.name = "Projekt-Julian-Nico"

include(":android")
include(":desktop")
include(":common")
include(":web")
include(":server")
include(":model")

