package utils

object StringUtils {

    fun formatYr(yr: Int): String {
        val formattedYr = if (yr == 0) 1 else yr
        return "${formattedYr}${getYrSuffix(yr)}"
    }

    private fun getYrSuffix(yr: Int): String {
        return if (yr < 0) yearBCE else yearCE
    }

    fun getEra(yr: Int): String {
        return when {
            yr <= -600 -> eraPrehistory
            yr <= 476 -> eraClassical
            yr <= 1450 -> eraEarlyModern
            else -> eraModern
        }
    }
}

const val eraPrehistory = "Prehistory"
const val eraClassical = "Classical Era"
const val eraEarlyModern = "Early Modern Era"
const val eraModern = "Modern Era"

const val yearBCE = "BCE"
const val yearCE = "CE"
