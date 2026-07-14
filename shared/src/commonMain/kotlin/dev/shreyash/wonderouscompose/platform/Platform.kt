package platform

sealed interface Platform {
    data class Android(val version: Int) : Platform
    data class Ios(val version: Double) : Platform
    data object Desktop : Platform

    sealed interface Web : Platform {
        data object Js : Web
        data object Wasm : Web
    }

    companion object
}

expect val platform: Platform