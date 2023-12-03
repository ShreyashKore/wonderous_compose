package ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.DpSize

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