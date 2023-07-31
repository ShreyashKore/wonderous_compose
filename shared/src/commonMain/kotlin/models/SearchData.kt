package models

data class SearchData(
    val year: Int,
    val id: Int,
    val imagePath: String,
    val keywords: String,
    val title: String,
    val aspectRatio: Double
) {
    val imageUrl get() = baseImagePath + imagePath;

    companion object {
        const val baseImagePath = "https://images.metmuseum.org/CRDImages/"
    }

    // used by the search helper tool:
    fun write(): String =
        "SearchData($year, $id, '$title', '$keywords', '$imagePath'${
            if (aspectRatio == 0.0) "" else ", $aspectRatio" // TODO: toFixed(2)
        })"
}