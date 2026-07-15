import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "dev.shreyash.wonderouscompose.MainKt"

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