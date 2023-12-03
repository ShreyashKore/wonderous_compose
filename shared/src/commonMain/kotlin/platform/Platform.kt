package platform

sealed interface Platform {
    data class Android(val version: Int) : Platform
    data class Ios(val version: Int) : Platform
    data object Desktop : Platform
    companion object
}

expect val platform: Platform