package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

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

        val placeables = measurables.map { it.measure(constraints) }

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