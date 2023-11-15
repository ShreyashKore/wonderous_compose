package ui.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


/**
 * Detects swipe gesture and returns direction in form of normalized offset.
 */
fun Modifier.eightWaySwipeDetector(
    minDragLength: Int = 20,
    onSwipe: (direction: Offset) -> Unit,
) = composed {
    var offset: Offset = Offset.Zero
    pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                if (sqrt(offset.x.pow(2) + offset.y.pow(2)) >= minDragLength) {
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

fun Modifier.simpleTransformable() = composed {
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    val transformableState = rememberTransformableState { zoomChange, panChange, rotationChange ->
        x += panChange.x
        y += panChange.y
        rotation += rotationChange
        scale *= zoomChange
    }
    graphicsLayer {
        translationX = x
        translationY = y
        scaleX = scale
        scaleY = scale
        rotationZ = rotation
    }.transformable(transformableState)
}