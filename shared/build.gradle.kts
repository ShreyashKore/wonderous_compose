import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.multiplatform)
    kotlin("native.cocoapods")
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
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
            withJs()
        }
    }
    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            binaryOption("bundleId", "com.shreyashkore.wonderouscompose")
            baseName = "shared"
            isStatic = true
        }
    }

//    @OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.kamel.image)
                implementation(libs.ktor.core)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.kotlinx.datetime)
                implementation(libs.image.loader)
                implementation(libs.insetsx)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kermit)
                implementation(libs.orbital)
                implementation(libs.precompose)
                implementation(libs.moko.mvvm)
            }
        }
        val noJsMain by getting {
            dependencies {
                implementation(libs.compose.webview.multiplatform)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.androidx.activityCompose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.serialization)
                implementation(libs.accompanist.permissions)
                implementation(libs.play.services.maps)
                implementation(libs.play.services.location)
                implementation(libs.maps.compose)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.ktor.client.java)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
            }
        }
    }
}


android {
    namespace = "org.shreyashkore.testkmp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

//        applicationId = "org.shreyashkore.testkmp.androidApp"
//        versionCode = 1
//        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
        sourceSets["main"].assets.srcDirs("src/androidMain/assets/fonts")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}
//android {
//    compileSdk = (findProperty("android.compileSdk") as String).toInt()
//    namespace = "com.shreyashkore.common"

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
//    sourceSets["main"].assets.srcDirs("src/androidMain/assets/fonts")

//    defaultConfig {
//        minSdk = (findProperty("android.minSdk") as String).toInt()
//        applicationId = "org.shreyashkore.wonderouscompose"
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlin {
//        jvmToolchain(11)
//    }
//}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.shreyashkore.testkmp.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}