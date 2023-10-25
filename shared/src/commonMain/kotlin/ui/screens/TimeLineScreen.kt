package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.getAssetPath
import ui.theme.fgColor
import utils.StringUtils.getYrSuffix
import utils.dashedBorder
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

val from = -3000
val to = 2200
val maxDuration = to - from

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimeLineScreen(
    selectedWonder: Wonder? = null
) = BoxWithConstraints {
    val scrollState = rememberScrollState()

    val mainLineHeight = maxDuration.dp
    val mainLineHeightPx = with(LocalDensity.current) {
        mainLineHeight.toPx()
    }
    val density = LocalDensity.current
    val padding = maxHeight / 2


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

    val yearInt = (getScrollFraction() * maxDuration - 3000).roundToInt()
    val yearSuffix = getYrSuffix(yearInt)

    Box(
        Modifier.verticalScroll(scrollState)
            .padding(vertical = padding)
            .height(mainLineHeight)
            .background(Color.Blue.copy(alpha = 0.2f))
    ) {

        Row {
            Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                TimeStrip(
                    yearInt, {
                        println("HITTTT $it")
                    }, range = from..to, step = 100
                )
            }
            LineGroup(::getScrollFraction, Modifier.weight(4f))
        }

        Column(
            Modifier.fillMaxWidth()
                .offset { IntOffset(0, scrollState.value) },
            horizontalAlignment = Alignment.End
        ) {

            Row(
                Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${yearInt.absoluteValue}",
                    fontSize = 24.sp
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    yearSuffix,
                    fontSize = 16.sp
                )

            }
            Spacer(
                Modifier.fillMaxWidth().height(1.dp)
                    .dashedBorder(BorderStroke(1.dp, Color.White), on = 8.dp, off = 8.dp)
            )
        }
    }

    val maxWidth = maxWidth;

    Box(
        Modifier.align(Alignment.BottomCenter).height(maxWidth)
            .padding(20.dp).width(100.dp).rotate(-90f).background(Color.DarkGray)

    ) {

        val badgeWidth = 20.dp

        LineGroup(::getScrollFraction, Modifier.padding(horizontal = 12.dp))
        Box(Modifier.offset {
            val maxWidth = maxWidth - 40.dp
            val offset = getScrollFraction() * maxWidth.roundToPx()
            val offset2 = getScrollFraction() * (maxWidth - badgeWidth).roundToPx()
            IntOffset(0, offset2.roundToInt())
        }.border(1.dp, Color.White).fillMaxWidth().height(badgeWidth))
    }

    AnimatedContent(
        eventDataList.firstOrNull { it.time == yearInt }
    ) { event ->
        if (event == null) {
            Spacer(Modifier)
        } else {
            Box(Modifier.padding(30.dp).background(Color.Magenta)) {
                Text(event.description)
            }
        }
    }

}

@Composable
fun LineGroup(
    getScrollFraction: () -> Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = PyramidsGiza,
                getScrollFraction
            )
            WonderLine(
                wonder = GreatWall,
                getScrollFraction
            )
            WonderLine(
                wonder = ChristRedeemer,
                getScrollFraction
            )
        }
        Spacer(Modifier.weight(.2f))
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = Petra,
                getScrollFraction,
            )
            WonderLine(
                wonder = MachuPicchu,
                getScrollFraction,
            )
        }
        Spacer(Modifier.weight(.2f))
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = Colosseum,
                getScrollFraction,
            )
            WonderLine(
                wonder = ChichenItza,
                getScrollFraction,
            )
            WonderLine(
                wonder = TajMahal,
                getScrollFraction,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderLine(
    wonder: Wonder,
    getScroll: () -> Float,
) {
    Line(
        from = wonder.startYr,
        to = wonder.endYr,
        rangeStart = -3000,
        rangeEnd = 2200,
        getScroll = getScroll,
        modifier = Modifier.background(
            color = wonder.fgColor, shape = RoundedCornerShape(percent = 100),
        )
    ) {
        Image(
            painterResource(wonder.getAssetPath("flattened.jpg")),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}


@Composable
fun TimeStrip(
    currentYear: Int,
    onHitEvent: (EventData) -> Unit,
    range: IntRange,
    step: Int,
) = BoxWithConstraints {
    val i = range.step(step)
    Row(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(i.count()) {
                Text("${i.elementAt(it).absoluteValue}")
            }
        }
        Column(Modifier.weight(1f)) {
            eventDataList.map {
                val offset =
                    this@BoxWithConstraints.maxHeight * (it.time.toFloat() + 3000) / (range.last - range.first)
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
    EventData(-3000, "Event 0"),
    EventData(-2700, "Event 23"),
    EventData(34, "Event 34"),
    EventData(55, "Event 55"),
)


@Composable
fun Line(
    from: Int,
    to: Int,
    rangeStart: Int,
    rangeEnd: Int,
    getScroll: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null,
) = BoxWithConstraints {
    val totalRange = rangeEnd - rangeStart
    val unitDistance = maxHeight / totalRange

    val minSpace = maxOf(100, (maxWidth / unitDistance).toInt())

    val range = to - from
    val safeFrom = (if (range < minSpace) from - (minSpace - range) / 2 else from) - rangeStart
    val safeRange = maxOf(range, minSpace)


    val fromFraction = safeFrom / totalRange.toFloat()
    val fraction = safeRange.toFloat() / totalRange
    val startOffset = unitDistance * safeFrom.toFloat()
    val fractionHeight = unitDistance * safeRange
    val thumbSize = maxOf(maxWidth, minOf(fractionHeight, unitDistance * 200))
    val thumbEndOffset = fractionHeight - thumbSize
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction)
            .offset(y = startOffset) then modifier,
    ) {
        if (content != null) {
            Box(Modifier.fillMaxWidth().height(thumbSize).offset {
                val scroll = ((getScroll() - fromFraction) / fraction).coerceIn(0f, 1f)
                println("SCROLLL ${scroll}")
                IntOffset(0, lerp(0, thumbEndOffset.roundToPx(), scroll))
            }) {
                content()
            }
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