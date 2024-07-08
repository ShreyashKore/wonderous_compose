plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization")
}

version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop")

    wasmJs {
        browser {

        }
        binaries.library()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(libs.kotlinx.datetime)

                implementation(libs.ktor.core)
            }
        }
        val desktopMain by getting {
            dependencies {
                // implementation("io.ktor:ktor-client-cio:2.2.1")
                implementation(libs.ktor.client.java)
                implementation(compose.desktop.common)
            }
        }
        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}
