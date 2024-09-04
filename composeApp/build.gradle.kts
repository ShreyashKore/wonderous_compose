import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.android.maps.secrets)
}

// Project Version
version = libs.versions.versionName.get()

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            // intermediate source set for everything except js and wasm
            group("nonWeb") {
                withAndroidTarget()
                withNative()
                withJvm()
            }
            group("web") {
                withJs()
                withWasmJs()
            }
        }
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                cssSupport { enabled.set(true) }
            }
        }
        binaries.executable()
    }

    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                cssSupport { enabled.set(true) }
            }
        }
        binaries.executable()
        useEsModules()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "com.shreyashkore.wonderouscompose")
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.image.loader)
        }

        val nonWebMain by getting {
            dependencies {
                implementation(libs.compose.webview.multiplatform)
            }
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.play.services.maps)
            implementation(libs.play.services.location)
            implementation(libs.maps.compose)
            implementation(libs.androidx.appcompat)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.java)
            implementation(project(":mapview-desktop"))
        }

        jsMain.dependencies {
            // TODO: replace with implementation("com.github.Hamamas:Kotlin-Wasm-Html-Interop:0.0.3-alpha")
            implementation(project(":composeWebInterop"))
            implementation(npm("ol", "9.2.4"))
            api(libs.ktor.client.js)
            api(libs.coil.network.ktor)
        }

        val wasmJsMain by getting
        wasmJsMain.dependencies {
            // TODO: replace with implementation("com.github.Hamamas:Kotlin-Wasm-Html-Interop:0.0.3-alpha")
            implementation(project(":composeWebInterop"))
            implementation(npm("ol", "9.2.4"))
        }
    }
}

android {
    val keyProp = run {
        val keyPropFile = rootProject.file("key.properties")
        if (keyPropFile.exists())
            Properties().apply { load(keyPropFile.inputStream()) }
        else null
    }

    namespace = "com.shreyashkore.wonderouscompose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.shreyashkore.wonderouscompose"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        if (keyProp != null) {
            create("release") {
                storePassword = keyProp.getProperty("storePassword")
                keyPassword = keyProp.getProperty("keyPassword")
                keyAlias = keyProp.getProperty("alias")
                storeFile = File(keyProp.getProperty("path"))
            }
        }
    }
    buildTypes {
        if (keyProp != null) {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.shreyashkore.wonderouscompose"
            packageVersion = libs.versions.versionName.get()
            description = "Port of Wonderous in Compose Multiplatform"
            copyright = "© 2024 Shreyash Kore. All rights reserved."
            vendor = "Gyanoba"
            licenseFile.set(project.file("../LICENSE.txt"))
            modules("java.net.http")

            macOS {
                iconFile.set(project.file("./launcher_icons/app_icon.icns"))
            }
            windows {
                iconFile.set(project.file("./launcher_icons/app_icon.ico"))
            }
            linux {
                iconFile.set(project.file("./launcher_icons/app_icon.png"))
            }
        }
    }
}