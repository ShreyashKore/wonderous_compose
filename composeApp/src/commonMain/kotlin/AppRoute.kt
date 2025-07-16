import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data class Wonder(val wonderId: String) : AppRoute

    @Serializable
    data class Timeline(val wonderId: String? = null) : AppRoute

    @Serializable
    data class ArtifactDetails(val artifactId: String) : AppRoute

    @Serializable
    data class Search(val wonderId: String) : AppRoute

    @Serializable
    data class Maps(val wonderId: String) : AppRoute

    @Serializable
    data class Video(val videoId: String) : AppRoute
}