package ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.DpSize
import kotlin.math.roundToInt

@Composable
fun SimpleGrid(
    modifier: Modifier = Modifier,
    columnCount: Int,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables =
            measurables.map { it.measure(constraints.copy(minHeight = 0, minWidth = 0)) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.chunked(columnCount).forEach { placeables ->
                placeables.forEach { placeable ->
                    placeable.place(x, y)
                    x += placeable.width
                }
                x = 0
                y += placeables.maxOf { it.height }
            }
        }
    }
}

@Composable
fun RotatedLayout(
    rotationDegrees: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    require(rotationDegrees == 90f || rotationDegrees == -90f) {
        "Only rotates to a right angle"
    }
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            Modifier.requiredSize(DpSize(maxHeight, maxWidth))
                .graphicsLayer { rotationZ = rotationDegrees }) {
            content()
        }
    }
}


/**
 * Offsets the composable by it's height and width's fractional values
 */
fun Modifier.fractionalOffset(widthFraction: Float, heightFraction: Float) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                (placeable.width * widthFraction).roundToInt(),
                (placeable.width * heightFraction).roundToInt()
            )
        }
    }