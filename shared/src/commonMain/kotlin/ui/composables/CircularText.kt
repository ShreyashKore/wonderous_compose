package ui.composables

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.pow

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
    evenWidth: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Layout(
        content = {
            text.forEach {
                Text(it, style = textStyle)
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val radiusPx = radius.toPx()
        val diameterPx = radiusPx * 2
        val textWidth = placeables.sumOf { it.width }.toFloat()
        val textWidthMid = textWidth / 2
        val textWidthAvg = (textWidth / placeables.size).toInt()
        layout(diameterPx.toInt(), diameterPx.toInt()) {
            var x = -textWidthMid + textWidthAvg / 2
            placeables.forEach { placeable ->
                val y = (radiusPx.pow(2) - x.pow(2)).pow(.5f)
                val angle = radiansToDegrees(atan((x) / y))
                placeable.placeRelativeWithLayer(
                    (x - placeable.width / 2f).toInt() + radiusPx.toInt(),
                    radiusPx.toInt() - y.toInt()
                ) {
                    rotationZ = angle
                }
                x += (if (evenWidth) textWidthAvg else placeable.width)
            }
        }
    }


}

private fun radiansToDegrees(angle: Float) =
    (angle * 180f / PI.toFloat()).let { if (it.isNaN()) 90f else it }

//       Canvas(modifier = modifier.size(radius * 2)) {
//           val radiusPx = radius.toPx()
//           val center = Offset(radiusPx, radiusPx)
//           val textLayoutResults = text.map { textMeasurer.measure(it, style = textStyle) }
//           val midWidth =
//               textLayoutResults.sumOf { it.getBoundingBox(0).width.toDouble() }.toFloat() / 2
//   //        var checker = 0f
//   //        val mid = textLayoutResults.firstOrNull {
//   //            checker += it.getBoundingBox(0).width
//   //            checker > midWidth
//   //        }
//           var i = 0f
//           withTransform({
//               translate(radiusPx, radiusPx)
//           }) {
//               textLayoutResults.forEach {
//                   val textWidth = it.size.width
//                   val textHeight = it.size.height
//
//                   val x = i - midWidth
//
//                   val y = (radiusPx.pow(2) - x.pow(2)).pow(.5f)
//                   val angle = atan((x) / y)
//                   val angleInDegrees = angle * 180f / PI.toFloat()
//
//                   println(
//                       " PYTHHHHA\t str ${it.layoutInput.text} x $x radiusPx $radiusPx xFromCenter $x  Angle $angleInDegrees"
//                   )
//   //                withTransform({
//   //                    rotate(angleInDegrees)
//   ////                    translate(x, y)
//   //                }) {
//   //                    drawText(it)
//   //                }
//                   i += textWidth
//               }
//
//           }
//
//       }
//textLayoutResults.forEach {
//    val textWidth = it.size.width
//    val textHeight = it.size.height
//
//    val x = i - midWidth
//
//    val y = (radiusPx.pow(2) - x.pow(2)).pow(.5f)
//    val angle = atan((x) / y)
//    val angleInDegrees = angle * 180f / PI.toFloat()
//    println(
//        " PYTHHHHA\t str ${it.layoutInput.text} x $x radiusPx $radiusPx xFromCenter $x  Angle $angleInDegrees"
//    )
//    withTransform({
//        rotate(angleInDegrees)
//        translate(center.x + x, y)
//    }) {
//        drawText(it)
//    }
//    i += textWidth
//}