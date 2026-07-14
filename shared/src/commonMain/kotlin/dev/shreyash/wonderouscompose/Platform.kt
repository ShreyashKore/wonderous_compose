package dev.shreyash.wonderouscompose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform