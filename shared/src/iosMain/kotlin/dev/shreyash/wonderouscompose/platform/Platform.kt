package dev.shreyash.wonderouscompose.platform

import dev.shreyash.wonderouscompose.platform.Foundation.NSFoundationVersionNumber

actual val platform: Platform = Platform.Ios(NSFoundationVersionNumber)