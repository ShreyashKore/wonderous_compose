package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import models.Wonder
import kotlin.math.roundToInt

val maxDuration = 100

@Composable
fun TimeLineScreen(
    selectedWonder: Wonder? = null
) = BoxWithConstraints {
    val scrollState = rememberScrollState()

    val mainLineHeight = 50.dp * maxDuration
    val mainLineHeightPx = with(LocalDensity.current) {
        mainLineHeight.toPx()
    }
    val density = LocalDensity.current
    val padding = maxHeight / 2

    val paddingPx = with(LocalDensity.current) {
        padding.roundToPx()
    }

    val scroll by remember {
        derivedStateOf {
            scrollState.value / +with(density) { maxHeight.roundToPx() } / 2
        }
    }

    val scrollFraction by remember {
        derivedStateOf {
            ((scrollState.value) / scrollState.maxValue.toFloat()).coerceIn(0f, 1f)
        }
    }

    fun getScrollFraction() = scrollFraction


    Box(
        Modifier.verticalScroll(scrollState).padding(vertical = padding).height(mainLineHeight)
            .background(Color.Blue.copy(alpha = 0.2f))
    ) {

        Row {
            Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                TimeStrip(24, {}, maxDuration)
            }
            LineGroup(::getScrollFraction, Modifier.weight(4f))
        }

        Box(Modifier.fillMaxWidth().offset { IntOffset(0, scroll) }
            .background(Color.Red.copy(alpha = 0.2f))) {
            Text("$scrollFraction")
        }
    }

    val maxWidth = maxWidth;

    Box(
        Modifier.align(Alignment.BottomCenter).height(maxWidth)
            .padding(20.dp).width(100.dp).rotate(-90f).background(Color.DarkGray)

    ) {

        val badgeWidth = 20.dp
        Box(Modifier.offset {
            val maxWidth = maxWidth - 40.dp
            val offset = getScrollFraction() * maxWidth.roundToPx()
            val offset2 = getScrollFraction() * (maxWidth - badgeWidth).roundToPx()
            IntOffset(0, offset2.roundToInt())
        }.background(Color.Green).fillMaxWidth().height(badgeWidth))
        LineGroup(::getScrollFraction, Modifier)
    }
}

@Composable
fun LineGroup(getScrollFraction: () -> Float, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxSize()
    ) {
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Line(from = 0, 100, maxDuration, Color.Magenta, getScrollFraction)
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Line(from = 30, 50, maxDuration, Color.Blue, getScrollFraction)
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Line(from = 0, 20, maxDuration, Color.Red, getScrollFraction)
            Line(from = 25, 60, maxDuration, Color.Green, getScrollFraction)
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Line(from = 80, 100, maxDuration, Color.Yellow, getScrollFraction)
        }
    }
}

@Composable
fun TimeStrip(
    currentYear: Int,
    onHitEvent: (EventData) -> Unit,
    totalTime: Int,
) = BoxWithConstraints {
    LaunchedEffect(currentYear) {
        val event = eventDataList.firstOrNull { it.time == currentYear }
        if (event != null) {
            onHitEvent(event)
        }
    }
    Row(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(200) {
                Box(Modifier.background(Color.Yellow).height(1.dp).width(5.dp))
            }
        }
        Column(Modifier.weight(1f)) {
            eventDataList.map {
                val offset = this@BoxWithConstraints.maxHeight * it.time.toFloat() / totalTime
                Box(Modifier.offset(y = offset).background(Color.Yellow)) {
                    Text(it.description)
                }
            }
        }
    }

}

data class EventData(
    val time: Int,
    val description: String,
)

val eventDataList = listOf(
    EventData(12, "Event 0"),
    EventData(23, "Event 23"),
    EventData(34, "Event 34"),
    EventData(55, "Event 55"),
)


@Composable
fun Line(
    from: Int,
    to: Int,
    total: Int,
    color: Color,
    getScroll: () -> Float,
    outlineOnly: Boolean = true,
) = BoxWithConstraints {
    val thumbSize = 100.dp

    val range = to - from
    val fromFraction = from / total.toFloat()
    val toFraction = to / total.toFloat()
    val fraction = range.toFloat() / total
    val startOffset = maxHeight * (from.toFloat() / total)
    val endOffset = (maxHeight * (to.toFloat() / total))
    val fractionHeight = maxHeight * fraction
    val thumbEndOffset = fractionHeight - thumbSize
    val fullHeight = maxHeight
    println("BoxConstraints $fullHeight")

    Box(
        Modifier.fillMaxWidth().fillMaxHeight(fraction).offset(y = startOffset).run {
            if (outlineOnly) border(
                color = color, width = 2.dp, shape = RoundedCornerShape(percent = 100)
            ) else background(
                color = color, shape = RoundedCornerShape(percent = 100),
            )
        },
    ) {
        if (!outlineOnly) {
            Box(Modifier.width(100.dp).height(thumbSize).offset {
                val scroll = ((getScroll() - fromFraction) / fraction).coerceIn(0f, 1f)
                IntOffset(0, lerp(0, thumbEndOffset.roundToPx(), scroll))
            }.background(Color.Cyan))
        }
    }
}


fun Offset.toIntOffset() = IntOffset(x = x.roundToInt(), y = y.roundToInt())


fun lerp(start: Float, end: Float, fraction: Float): Float = (1 - fraction) * start + fraction * end
fun lerp(start: Int, end: Int, fraction: Float): Int =
    start + ((end - start) * fraction.toDouble()).roundToInt()


fun inverseLerp(start: Float, end: Float, value: Float): Float {
    return (value - start) / (end - start)
}