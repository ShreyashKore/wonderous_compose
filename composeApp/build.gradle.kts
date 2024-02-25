import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinx.serialization)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("noJs") {
                withAndroidTarget()
                withNative()
                withJvm()
            }
            group("jsWasm") {
                withJs()
                withWasm()
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    js(IR) {
        browser()
        binaries.executable()
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
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            // implementation(libs.kamel.image) // https://github.com/Kamel-Media/Kamel/issues/85
            implementation(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            // implementation(libs.orbital)
            implementation(libs.precompose) // https://github.com/Tlaster/PreCompose/issues/69
            // implementation(libs.moko.mvvm) // https://github.com/icerockdev/moko-mvvm/issues/261
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }

        val noJsMain by getting {
            dependencies {
                implementation(libs.compose.webview.multiplatform)
            }
        }

        androidMain.dependencies {
//            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.play.services.maps)
            implementation(libs.play.services.location)
            implementation(libs.maps.compose)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.java)
        }

        jsMain.dependencies {
            // TODO: replace with implementation("com.github.Hamamas:Kotlin-Wasm-Html-Interop:0.0.3-alpha")
            implementation(project(":composeWebInterop"))
        }

        val wasmJsMain by getting
        wasmJsMain.dependencies {
            // TODO: replace with implementation("com.github.Hamamas:Kotlin-Wasm-Html-Interop:0.0.3-alpha")
            implementation(project(":composeWebInterop"))
        }
    }
}

android {
    val keyProp = Properties().apply {
        val keyPropFile = rootProject.file("key.properties")
        load(keyPropFile.inputStream())
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
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            storePassword = keyProp.getProperty("storePassword")
            keyPassword = keyProp.getProperty("keyPassword")
            keyAlias = keyProp.getProperty("alias")
            storeFile = File(keyProp.getProperty("path"))
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
//        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.shreyashkore.wonderouscompose"
            packageVersion = "1.0.0"
            modules("java.net.http")
        }
    }
}

compose.experimental {
    web.application {}
}