plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
}

tasks.register("generateIosVersionConfig") {
    doLast {
        val configDir = file("iosApp/Configuration")
        configDir.mkdirs()
        file("$configDir/Version.xcconfig").writeText(
            """
            CURRENT_PROJECT_VERSION=${libs.versions.versionCode.get()}
            MARKETING_VERSION=${libs.versions.versionName.get()}
            """.trimIndent()
        )
    }
}