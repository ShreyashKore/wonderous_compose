package ui.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.abs
import kotlin.math.roundToInt


/**
 * Detects swipe gesture and returns direction in form of normalized offset.
 */
fun Modifier.eightWaySwipeDetector(onSwipe: (direction: Offset) -> Unit) = composed {
    var offset: Offset = Offset.Zero
    pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                if (abs(offset.x) > 20 || abs(offset.y) > 20) {
                    val normalizedOffset = offset.normalizedDirection()
                    if (normalizedOffset != Offset.Zero) onSwipe(normalizedOffset)
                }
                offset = Offset.Zero
            },
        ) { change, dragAmount ->
            change.consume()
            offset += dragAmount
        }
    }
}

fun Offset.normalizedDirection(): Offset {
    val offset = when (y / x) {
        in Float.NEGATIVE_INFINITY..<-2.41f -> Offset(0f, 1f)
        in -2.41f..-0.41f -> Offset(-1f, 1f)
        in -0.41f..0.41f -> Offset(-1f, 0f)
        in 0.41f..2.41f -> Offset(-1f, -1f)
        in 2.41f..Float.POSITIVE_INFINITY -> Offset(0f, -1f)
        else -> Offset.Zero
    }
    return if (x < 0) { // left
        offset
    } else { // right
        -offset
    }
}

fun Offset.roundToIntOffset() = IntOffset(
    x.roundToInt(), y.roundToInt()
)


fun extrapolate(start1: Float, end1: Float, start2: Float, end2: Float, x: Float): Float {
    val slope = (end2 - start2) / (end1 - start1)
    val y = start2 + (slope * (x - start1))
    return y
}