val ktor_version = extra["ktor_version"].toString()
val coroutines_version = extra["coroutines_version"].toString()
val lifecycle_version = "2.4.0"

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.0"
}

group = "de.hsflensburg"
version = "1.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser {
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.material)
                api(compose.foundation)

                implementation(project(":model"))
                implementation("com.benasher44:uuid:0.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val commonJvmAndroid = create("commonJvmAndroid") {
            dependsOn(commonMain)
        }
        val androidMain by getting {
            dependsOn(commonJvmAndroid)
            dependencies {
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")

                implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
                implementation("androidx.compose.runtime:runtime:1.3.3")
                implementation("androidx.compose.runtime:runtime-livedata:1.3.3")
                implementation("androidx.compose.runtime:runtime-rxjava2:1.3.3")

                implementation("io.ktor:ktor-client-cio:$ktor_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependsOn(commonJvmAndroid)
            dependencies {
                api(compose.preview)
                api(compose.runtime)
                implementation("io.ktor:ktor-client-cio:$ktor_version")
            }
        }
        val desktopTest by getting

        val jsMain by getting {
            dependencies{
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
        val jsTest by getting
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



