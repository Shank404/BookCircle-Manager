val ktor_version = extra["ktor_version"].toString()
val kotlin_version = extra["kotlin_version"].toString()
val logback_version = extra["logback_version"].toString()
val exposed_version = extra["exposed_version"].toString()
val mysql_connection_version = extra["mysql_connection_version"].toString()

plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "2.2.1"
    kotlin("plugin.serialization") version "1.4.31"
}

group = "de.hsflensburg"
version = "1.0"
application {
    mainClass.set("de.hsflensburg.server.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":model"))

    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("mysql:mysql-connector-java:$mysql_connection_version")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:7.15.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation("io.ktor:ktor-server-content-negotiation:2.1.3")
    implementation("org.xerial:sqlite-jdbc:3.39.4.0")
    implementation(project(mapOf("path" to ":common")))

    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:2.2.1")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("de.nycode:bcrypt:2.2.0")
}