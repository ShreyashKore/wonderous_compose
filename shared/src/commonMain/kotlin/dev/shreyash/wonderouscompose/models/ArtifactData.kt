package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ArtifactData(
    @SerialName("objectID")
    val objectId: Int,
    val title: String,
    @SerialName("primaryImage")
    val image: String? = null,
    @SerialName("objectDate")
    val date: String? = null,
    @SerialName("objectName")
    val objectType: String? = null,
    val period: String? = null,
    val country: String? = null,
    val medium: String? = null,
    val dimension: String? = null,
    val classification: String? = null,
    val culture: String? = null,
    val objectBeginYear: Int? = null,
    val objectEndYear: Int? = null,
)