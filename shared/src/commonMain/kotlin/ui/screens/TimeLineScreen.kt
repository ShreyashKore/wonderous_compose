package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import models.AllTimeLineEvents
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
import platform.Platform
import platform.platform
import ui.AppIcons
import ui.composables.AppIconButton
import ui.composables.RotatedLayout
import ui.getAssetPath
import ui.theme.Raleway
import ui.theme.TenorSans
import ui.theme.accent1
import ui.theme.accent2
import ui.theme.black
import ui.theme.fgColor
import ui.theme.greyStrong
import ui.theme.offWhite
import ui.theme.white
import ui.utils.lerp
import utils.StringUtils.getYrSuffix
import utils.dashedBorder
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

const val StartYear = -3000
const val EndYear = 2200
const val timelineDuration = EndYear - StartYear

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLineScreen(
    selectedWonder: Wonder? = null,
    onClickBack: () -> Unit,
) = BoxWithConstraints {
    val scrollState = rememberScrollState()
    var scale by remember { mutableStateOf(1.dp) }
    val timelineHeight = timelineDuration * scale
    val padding = maxHeight / 2

    LaunchedEffect(selectedWonder) {
        val initialScrollPos = (((selectedWonder?.startYr?.minus(StartYear))?.toFloat()
            ?: 200f) / timelineDuration * scrollState.maxValue).toInt()
        // first snap timeline to scroll pos just above the selected wonder
        scrollState.scrollTo(initialScrollPos - 2000)
        scrollState.animateScrollTo(
            initialScrollPos,
            animationSpec = tween(durationMillis = 2000)
        )
    }

    val scrollFraction by remember {
        derivedStateOf {
            ((scrollState.value) / scrollState.maxValue.toFloat()).coerceIn(0f, 1f)
        }
    }

    fun getScrollFraction() = scrollFraction

    val currentYear = (getScrollFraction() * timelineDuration - 3000).roundToInt()

    Box(
        Modifier.background(black)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale = (scale * zoom).coerceIn(0.5f.dp, 2.dp)
                }
            }
            .verticalScroll(scrollState)
            .padding(vertical = padding)
            .height(timelineHeight)
    ) {

        Row {
            TimeStripAndEventMarkers(
                currentYear = currentYear,
                range = StartYear..EndYear,
                step = 100,
                modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 16.dp),
            )

            WonderLines(
                getScrollFraction = ::getScrollFraction,
                selectedWonder = selectedWonder,
                modifier = Modifier.weight(4f)
            )
        }

        CurrentYearLine(
            currentYear = currentYear,
            modifier = Modifier.fillMaxWidth()
                .offset { IntOffset(0, scrollState.value) }
        )
    }

    // Small bottom timeline
    SmallTimeLine(
        ::getScrollFraction,
        modifier = Modifier.padding(20.dp).clip(RoundedCornerShape(8.dp))
            .background(greyStrong).fillMaxWidth().height(72.dp)
            .align(Alignment.BottomCenter),
        highLightedWonder = selectedWonder
    )

    // Events popup
    AnimatedContent(
        targetState = AllTimeLineEvents.firstOrNull { it.year in currentYear.eventHighlightRange },
        modifier = Modifier.fillMaxWidth()
            .padding(top = 80.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        transitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(durationMillis = 500, delayMillis = 500)
            ) + fadeIn(tween(delayMillis = 500)) togetherWith
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(delayMillis = 2000)
                    ) + fadeOut()
        },
        contentAlignment = Alignment.Center
    ) { event ->
        if (event == null) {
            Spacer(Modifier)
        } else {
            TimelineEventCard(
                year = event.year,
                text = event.description,
                darkMode = false,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            titleContentColor = white
        ),
        title = {
            Text("GLOBAL TIMELINE", fontSize = 14.sp, fontFamily = Raleway)
        },
        navigationIcon = {
            AppIconButton(
                iconPath = AppIcons.Prev,
                contentDescription = "Back",
                onClick = onClickBack
            )
        }
    )
}


@Composable
fun CurrentYearLine(
    currentYear: Int,
    modifier: Modifier,
) {
    val yearSuffix = getYrSuffix(currentYear)
    Column(
        modifier.requiredHeight(50.dp).offset(y = (-50).dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        val textShadow = Shadow(offset = Offset(1f, 1f), blurRadius = 2f)

        Row(
            Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${currentYear.absoluteValue}",
                fontSize = 24.sp,
                color = white,
                fontFamily = TenorSans,
                style = TextStyle(shadow = textShadow)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                yearSuffix,
                fontSize = 16.sp,
                color = white,
                fontFamily = TenorSans,
                style = TextStyle(shadow = textShadow)
            )

        }
        Spacer(
            Modifier.fillMaxWidth().height(1.dp)
                .dashedBorder(
                    BorderStroke(Dp.Hairline, Color.White),
                    on = 4.dp, off = 8.dp
                )
        )
    }
}

@Composable
fun SmallTimeLine(
    getScrollFraction: (() -> Float)? = null,
    scrollThumbWidth: Dp = 72.dp,
    modifier: Modifier = Modifier,
    highLightedWonder: Wonder? = null,
) = RotatedLayout(rotationDegrees = -90f, modifier = modifier) {

    BoxWithConstraints {
        WonderLines(
            getScrollFraction = { 0f },
            modifier = Modifier.padding(12.dp),
            outlineOnly = true,
            selectedWonder = highLightedWonder
        )
        if (getScrollFraction != null) {
            // Scroll Thumb : only shown when scroll info is provided
            Box(
                Modifier.offset {
                    val offset = ((maxHeight - scrollThumbWidth) * getScrollFraction()).roundToPx()
                    IntOffset(0, offset)
                }.border(1.dp, offWhite, RoundedCornerShape(8.dp)).fillMaxWidth()
                    .height(scrollThumbWidth)
            )
        }
    }
}


@Composable
fun WonderLines(
    getScrollFraction: () -> Float,
    selectedWonder: Wonder? = null,
    outlineOnly: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(Modifier.weight(.2f))
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = PyramidsGiza,
                highLighted = selectedWonder == PyramidsGiza,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction
            )
            WonderLine(
                wonder = GreatWall,
                highLighted = selectedWonder == GreatWall,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction
            )
            WonderLine(
                wonder = ChristRedeemer,
                highLighted = selectedWonder == ChristRedeemer,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction
            )
        }
        Spacer(Modifier.weight(.2f))
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = Petra,
                highLighted = selectedWonder == Petra,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction,
            )
            WonderLine(
                wonder = MachuPicchu,
                highLighted = selectedWonder == MachuPicchu,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction,
            )
        }
        Spacer(Modifier.weight(.2f))
        Box(Modifier.weight(1f).fillMaxHeight()) {
            WonderLine(
                wonder = Colosseum,
                highLighted = selectedWonder == Colosseum,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction,
            )
            WonderLine(
                wonder = ChichenItza,
                highLighted = selectedWonder == ChichenItza,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction,
            )
            WonderLine(
                wonder = TajMahal,
                highLighted = selectedWonder == TajMahal,
                outlineOnly = outlineOnly,
                getScroll = getScrollFraction,
            )
        }
        Spacer(Modifier.weight(.2f))
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderLine(
    wonder: Wonder,
    highLighted: Boolean = true,
    outlineOnly: Boolean = false,
    getScroll: () -> Float,
) {
    val bgColor =
        if (outlineOnly)
            if (highLighted) accent2 else Color.Transparent
        else wonder.fgColor

    Line(
        start = wonder.startYr,
        end = wonder.endYr,
        totalRangeStart = StartYear,
        totalRangeEnd = EndYear,
        getScroll = getScroll,
        modifier = Modifier.background(
            color = bgColor,
            shape = RoundedCornerShape(percent = 100),
        ).run {
            if (outlineOnly) border(1.dp, accent2, RoundedCornerShape(percent = 100))
            else this
        }
    ) {
        if (!outlineOnly)
            Image(
                painterResource(wonder.getAssetPath("flattened.jpg")),
                contentDescription = wonder.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxSize()
                    .clip(CircleShape),
                colorFilter = if (highLighted) null else ColorFilter.tint(
                    color = wonder.fgColor,
                    // Color blend mode is not supported on Android versions below 29
                    blendMode = if (platform is Platform.Android && platform.version < 29) BlendMode.Overlay
                    else BlendMode.Color
                ),
            )
    }
}


@Composable
private fun TimeStripAndEventMarkers(
    currentYear: Int,
    range: IntRange,
    step: Int,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    val i = range.step(step)
    LazyColumn(
        Modifier.weight(1f).fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(i.count()) {
            Text(
                "${i.elementAt(it).absoluteValue}",
                color = white,
                fontSize = 13.sp
            )
        }
    }
    Box(Modifier.weight(.6f).fillMaxHeight()) {
        AllTimeLineEvents.map {
            val highLighted = it.year in currentYear.eventHighlightRange
            val verticalBias = lerp(
                start = -1f,
                stop = 1f,
                fraction = (it.year.toFloat() + 3000) / (range.last - range.first).toFloat()
            )
            EventMarker(
                highLighted = highLighted,
                modifier = Modifier.align(BiasAlignment(0f, verticalBias))
            )
        }
    }
}

@Composable
private fun EventMarker(
    highLighted: Boolean,
    modifier: Modifier,
) {
    val size by animateDpAsState(if (highLighted) 8.dp else 3.dp)
    val blurRadius by animateDpAsState(if (highLighted) 10.dp else 1.dp)
    Box(
        modifier
            .offset(y = (-4).dp)
            .size(size)
            .background(accent1, CircleShape)
            .blur(blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            .background(accent1, CircleShape)
    )
}


@Composable
fun Line(
    start: Int,
    end: Int,
    totalRangeStart: Int,
    totalRangeEnd: Int,
    getScroll: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit)? = null,
) = BoxWithConstraints {
    val totalRange = totalRangeEnd - totalRangeStart
    val dpPerUnit = maxHeight / totalRange

    val minRange = maxOf(100, (maxWidth / dpPerUnit).toInt())

    val range = end - start
    // For some wonders their range is too small to display correctly so we define minRange
    val safeStart =
        (if (range < minRange) start - (minRange - range) / 2 else start) - totalRangeStart
    val safeRange = maxOf(range, minRange)


    val fractionOfTotalRange = safeRange.toFloat() / totalRange
    val safeStartOffset = dpPerUnit * safeStart.toFloat()
    val safeHeightDp = dpPerUnit * safeRange
    val thumbSize = maxOf(maxWidth, minOf(safeHeightDp, 300.dp))

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(fractionOfTotalRange)
            .offset(y = safeStartOffset) then modifier,
    ) {
        if (content != null) {
            Box(Modifier.fillMaxWidth().height(thumbSize).offset {
                val startFraction = safeStart / totalRange.toFloat()
                val thumbMaxOffsetDp = safeHeightDp - thumbSize
                val scroll = ((getScroll() - startFraction) / fractionOfTotalRange).coerceIn(0f, 1f)
                val offsetPx = lerp(0, thumbMaxOffsetDp.roundToPx(), scroll)
                IntOffset(0, offsetPx)
            }) {
                content()
            }
        }
    }
}


/**
 * Range of year in which the event popup is shown and event marker is highlighted
 */
private val Int.eventHighlightRange get() = (this - 4..this + 4)