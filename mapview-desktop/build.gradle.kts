import org.gradle.kotlin.dsl.kotlin

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization")
}

version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        val desktopMain by getting {
            dependencies {
                // implementation("io.ktor:ktor-client-cio:2.2.1")
                implementation(libs.ktor.client.java)
                implementation(compose.desktop.common)
            }
        }
    }
}
