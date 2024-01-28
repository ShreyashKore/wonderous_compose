package ui


object AppIcons {
    val Close = getIconPath("close")
    val ShareIos = getIconPath("share-ios")
    val ShareAndroid = getIconPath("share-android")
    val Prev = getIconPath("prev")
    val ZoomIn = getIconPath("zoom-in")
    val Expand = getIconPath("expand")
    val Download = getIconPath("download")
    val Menu = getIconPath("menu")
    val ResetLocation = getIconPath("reset-location")
    val Wallpaper = getIconPath("wallpaper")
    val Info = getIconPath("info")
    val FullscreenExit = getIconPath("fullscreen-exit")
    val Timeline = getIconPath("timeline")
    val CloseLarge = getIconPath("close-large")
    val North = getIconPath("north")
    val Fullscreen = getIconPath("fullscreen")
    val Search = getIconPath("search")
    val Collection = getIconPath("collection")
    val NextLarge = getIconPath("next-large")
    val ZoomOut = getIconPath("zoom-out")
    const val Github = "images/common/icons/github-logo.png"

    private fun getIconPath(name: String) = "images/common/icons/icon-$name.png"
}
