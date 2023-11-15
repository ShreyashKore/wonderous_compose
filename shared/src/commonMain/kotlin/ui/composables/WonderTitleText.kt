package ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import models.ChristRedeemer
import models.Colosseum
import models.Wonder


@Composable
fun WonderTitleText(
    wonder: Wonder,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    enableShadows: Boolean = false
) {
    val smallText = wonder in setOf(ChristRedeemer, Colosseum)
    val spanStyle = style.toSpanStyle().copy(
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = if (smallText) 48.sp else 54.sp,
        shadow = if (enableShadows) Shadow() else null,
    )

    val pieces = wonder.title.lowercase().split(" ")

    // TextSpan builder, figures out whether to use small text, and adds linebreak or space (or nothing).
    fun AnnotatedString.Builder.buildTextSpan(text: String) {
        val smallWords = setOf("of", "the")
        val useSmallText = (text.trim() in smallWords)
        val i = pieces.indexOf(text)
        val addLinebreak = i == 0 && pieces.size > 1
        val addSpace = !addLinebreak && i < pieces.size - 1
        val formattedText = if (useSmallText) text.lowercase() else text.capitalize()

        withStyle(
            if (useSmallText) spanStyle.copy(fontSize = 20.sp) else spanStyle
        ) {
            append("$formattedText${if (addLinebreak) "\n" else if (addSpace) " " else ""}")
        }
    }

    val annotatedString = buildAnnotatedString {
        pieces.forEach { buildTextSpan(it) }
    }
    Text(annotatedString, modifier = modifier, textAlign = TextAlign.Center)
}