package utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.eraClassical
import wonderouscompose.composeapp.generated.resources.eraEarlyModern
import wonderouscompose.composeapp.generated.resources.eraModern
import wonderouscompose.composeapp.generated.resources.eraPrehistory
import wonderouscompose.composeapp.generated.resources.yearBCE
import wonderouscompose.composeapp.generated.resources.yearCE

object StringUtils {

    @Composable
    fun formatYr(yr: Int): String {
        val formattedYr = if (yr == 0) 1 else yr
        return "$formattedYr ${getYrSuffix(yr)}"
    }

    @Composable
    fun getYrSuffix(yr: Int): String {
        return stringResource(if (yr < 0) yearBCE else yearCE)
    }

    fun getEra(yr: Int): StringResource {
        return when {
            yr <= -600 -> eraPrehistory
            yr <= 476 -> eraClassical
            yr <= 1450 -> eraEarlyModern
            else -> eraModern
        }
    }
}

val eraPrehistory = Res.string.eraPrehistory
val eraClassical = Res.string.eraClassical
val eraEarlyModern = Res.string.eraEarlyModern
val eraModern = Res.string.eraModern

val yearBCE = Res.string.yearBCE
val yearCE = Res.string.yearCE
