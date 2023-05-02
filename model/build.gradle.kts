val ktor_version = extra["ktor_version"].toString()
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.31"
}

group = "de.hsflensburg"
version = "1.0"


repositories {
    mavenCentral()
}
kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
   jvm() {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }

    js(IR) {
        browser {
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("com.benasher44:uuid:0.6.0")
            }
        }
    }
}