package ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Naive implementation of Layout which arranges the [text] on a circular path.
 *
 * To avoid separating graphemes; we just force user to pass [text] as list of graphemes
 */
@Composable
fun CircularText(
    text: List<String>,
    radius: Dp,
    textStyle: TextStyle = LocalTextStyle.current,
    letterSpacing: TextUnit = 1.sp,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier) {
        val radiusPx = radius.toPx()
        val circumference = (2 * PI * radiusPx).toFloat()

        val textLayoutResults = text.map { textMeasurer.measure(it, style = textStyle) }
        val totalLength =
            textLayoutResults.sumOf { it.size.width } + letterSpacing.toPx() * text.size - 1
        val totalAngle = totalLength / circumference * 360f

        val eachAngle = totalAngle / text.size

        textLayoutResults.forEachIndexed { i, tLR ->
            val angle = i * eachAngle + eachAngle / 2 - totalAngle / 2 - 90f
            val angleInRads = toRadians(degrees = angle)
            val x = radiusPx * cos(angleInRads).toFloat()
            val y = radiusPx * sin(angleInRads).toFloat()
            withTransform(
                transformBlock = {
                    translate(
                        center.x - tLR.size.width / 2f + x,
                        center.y - tLR.size.height / 2f + y
                    )
                    rotate(angle + 90f, Offset(tLR.size.width / 2f, tLR.size.height / 2f))
                }
            ) {
                drawText(tLR)
            }
        }
    }
}

private fun toRadians(degrees: Float) = (degrees / 360) * PI * 2