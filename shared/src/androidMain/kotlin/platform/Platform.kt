package platform

import android.os.Build

actual val platform: Platform = Platform.Android(Build.VERSION.SDK_INT)