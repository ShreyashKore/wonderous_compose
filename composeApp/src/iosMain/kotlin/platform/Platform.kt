package platform

import platform.Foundation.NSFoundationVersionNumber

actual val platform: Platform = Platform.Ios(NSFoundationVersionNumber)