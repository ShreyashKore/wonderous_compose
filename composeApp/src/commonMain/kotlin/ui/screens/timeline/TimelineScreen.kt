package ui.screens.timeline

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
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import models.AllTimelineEvents
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.TimelineEvent
import models.Wonder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import platform.Platform
import ui.composables.AppIconButton
import ui.flattenedImage
import ui.screens.timeline.components.SmallTimeline
import ui.screens.timeline.components.TimelineEventCard
import ui.theme.Raleway
import ui.theme.TenorSans
import ui.theme.accent1
import ui.theme.black
import ui.theme.fgColor
import ui.theme.greyStrong
import ui.theme.white
import ui.utils.filePainterResource
import ui.utils.lerp
import utils.StringUtils.getYrSuffix
import utils.dashedBorder
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_prev
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    selectedWonder: Wonder? = null,
    onClickBack: () -> Unit,
) = BoxWithConstraints(
    Modifier.background(black).windowInsetsPadding(WindowInsets.safeDrawing)
) {
    val timelineState = rememberTimelineState()

    val verticalPadding = maxHeight / 2

    LaunchedEffect(selectedWonder) {
        // If a wonder is selected; animate to the start year of that wonder
        selectedWonder?.let { timelineState.animateRevealYear(it.startYr) }
    }

    val currentTimelineEvent = timelineState.currentTimelineEvent

    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(currentTimelineEvent) {
        // Whenever we encounter an event perform a haptic feedback
        if (currentTimelineEvent != null) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    Box(
        Modifier.fillMaxWidth().pointerInput(Unit) {
            detectTransformGestures { _, _, zoom, _ ->
                timelineState.setScale(zoom)
            }
        }.verticalScroll(timelineState.scrollState).padding(vertical = verticalPadding)
            .height(timelineState.timelineHeight), contentAlignment = Alignment.Center
    ) {

        Row(Modifier.widthIn(max = 600.dp)) {

            Spacer(Modifier.width(16.dp))

            TimeMarkers(
                yearRange = StartYear..EndYear,
                step = 100,
                modifier = Modifier.weight(0.4f).fillMaxHeight(),
            )

            EventMarkers(
                yearRange = StartYear..EndYear,
                currentYearHighlightRange = timelineState.currentYearHighlightRange,
                allEvents = AllTimelineEvents,
                modifier = Modifier.weight(0.4f).fillMaxHeight(),
            )

            TimelineTracksLayout(
                modifier = Modifier.weight(4f)
            ) { wonder ->
                WonderTrackWithStickyImage(
                    wonder = wonder,
                    isSelected = selectedWonder == wonder,
                    getCurrentYear = { timelineState.currentYear },
                )
            }
        }
    }

    CurrentYearLine(
        currentYear = timelineState.currentYear,
        modifier = Modifier.align(Alignment.Center),
    )

    // Small bottom timeline
    Box(
        Modifier.widthIn(max = 800.dp).align(Alignment.BottomCenter).padding(20.dp)
    ) {
        SmallTimeline(
            getScrollFraction = { timelineState.scrollFraction },
            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(greyStrong).fillMaxWidth()
                .height(72.dp),
            highLightedWonder = selectedWonder
        )
    }


    // Events popup
    AnimatedContent(
        targetState = currentTimelineEvent,
        modifier = Modifier.align(Alignment.TopCenter).widthIn(max = 800.dp).padding(top = 80.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        transitionSpec = { eventPopupTransitionSpec },
        contentAlignment = Alignment.Center
    ) { event ->
        if (event == null) {
            Spacer(Modifier)
        } else {
            TimelineEventCard(
                year = event.year,
                text = event.description,
                darkMode = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            titleContentColor = white,
        ),
        title = {
            Text("GLOBAL TIMELINE", fontSize = 14.sp, fontFamily = Raleway)
        },
        navigationIcon = {
            AppIconButton(
                icon = Res.drawable.icon_prev,
                contentDescription = "Back",
                onClick = onClickBack,
            )
        },
    )
}


@Composable
fun CurrentYearLine(
    currentYear: Int,
    modifier: Modifier,
) {
    val yearSuffix = getYrSuffix(currentYear)
    Column(
        // Offsetting by half the height to make line at the bottom align with screen center
        modifier.requiredHeight(50.dp).offset(y = (-25).dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        val textShadow = Shadow(offset = Offset(1f, 1f), blurRadius = 2f)

        Row(
            Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically
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
            Modifier.fillMaxWidth().height(1.dp).dashedBorder(
                BorderStroke(Dp.Hairline, Color.White), on = 2.dp, off = 4.dp
            )
        )
    }
}


@Composable
fun TimelineTracksLayout(
    modifier: Modifier = Modifier,
    trackItemContent: @Composable (wonder: Wonder) -> Unit,
) {
    Row(
        modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(Modifier.weight(.2f))
        BoxWithConstraints(Modifier.weight(1f).fillMaxHeight()) {
            TrackItem(wonder = PyramidsGiza, content = trackItemContent)
            TrackItem(wonder = GreatWall, content = trackItemContent)
            TrackItem(wonder = ChristRedeemer, content = trackItemContent)
        }
        Spacer(Modifier.weight(.2f))
        BoxWithConstraints(Modifier.weight(1f).fillMaxHeight()) {
            TrackItem(wonder = Petra, content = trackItemContent)
            TrackItem(wonder = MachuPicchu, content = trackItemContent)
        }
        Spacer(Modifier.weight(.2f))
        BoxWithConstraints(Modifier.weight(1f).fillMaxHeight()) {
            TrackItem(wonder = Colosseum, content = trackItemContent)
            TrackItem(wonder = ChichenItza, content = trackItemContent)
            TrackItem(wonder = TajMahal, content = trackItemContent)
        }
        Spacer(Modifier.weight(.2f))
    }
}


@Composable
inline fun BoxWithConstraintsScope.TrackItem(
    wonder: Wonder,
    content: @Composable (wonder: Wonder) -> Unit,
) {
    val start = (wonder.startYr.toFloat() - StartYear) / TimelineDuration * maxHeight
    val end = (wonder.endYr.toFloat() - StartYear) / TimelineDuration * maxHeight
    val minRange = maxWidth * 1.5f // should be at least as high as it is wide

    val range = end - start
    // For some wonders their range is too small to display correctly so we define safeRange
    val safeRange = maxOf(range, minRange)
    Box(
        Modifier.fillMaxWidth().height(safeRange).offset(0.dp, start),
    ) {
        content(wonder)
    }
}


@Composable
fun WonderTrackWithStickyImage(
    wonder: Wonder,
    isSelected: Boolean,
    getCurrentYear: () -> Int,
) {
    BoxWithConstraints(
        Modifier.fillMaxSize().background(
            color = wonder.fgColor,
            shape = RoundedCornerShape(percent = 100),
        ).padding(6.dp)
    ) {
        val startYear = wonder.startYr
        val yearRange = wonder.endYr - wonder.startYr
        val offsetEnd = (maxHeight - ThumbSize).coerceAtLeast(minimumValue = 0.dp)

        Image(
            painter = filePainterResource(wonder.flattenedImage),
            modifier = Modifier.fillMaxWidth().height(ThumbSize)
                .offset {
                    val curFraction =
                        ((getCurrentYear().toFloat() - startYear) / yearRange).coerceIn(0f, 1f)
                    val vertOffset = lerp(0, offsetEnd.toPx().roundToInt(), curFraction)
                    IntOffset(0, vertOffset)
                }.clip(CircleShape),
            contentDescription = stringResource(wonder.title),
            contentScale = ContentScale.Crop,
            colorFilter = if (isSelected) null else ColorFilter.tint(
                color = wonder.fgColor, blendMode = blendMode
            ),
        )
    }
}

@Composable
private fun TimeMarkers(
    yearRange: IntRange, step: Int, modifier: Modifier = Modifier
) {
    val years = remember(yearRange) { yearRange.step(step) }
    LazyColumn(
        modifier, verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(years.count()) {
            Text(
                "${years.elementAt(it).absoluteValue}", color = white, fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun EventMarkers(
    yearRange: IntRange,
    currentYearHighlightRange: IntRange,
    allEvents: List<TimelineEvent>,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        allEvents.map {
            val highlighted = it.year in currentYearHighlightRange
            val verticalBias = lerp(
                start = -1f,
                stop = 1f,
                fraction = (it.year.toFloat() - yearRange.first) / (yearRange.last - yearRange.first).toFloat()
            )
            key(it) {
                EventMarker(
                    highLighted = highlighted,
                    modifier = Modifier.align(BiasAlignment(0f, verticalBias))
                )
            }
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
        modifier.offset(y = (-4).dp).size(size).background(accent1, CircleShape)
            .blur(blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            .background(accent1, CircleShape)
    )
}


/**
 * Color blend mode is not supported on Android versions below 29
 */
val blendMode: BlendMode
    get() {
        val platform = platform.platform
        return if (platform is Platform.Android && platform.version < 29) BlendMode.Overlay else BlendMode.Color
    }

private val AnimatedContentTransitionScope<TimelineEvent?>.eventPopupTransitionSpec
    get() = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(durationMillis = 500, delayMillis = 500)
    ) + fadeIn(tween(delayMillis = 500)) togetherWith slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(delayMillis = 2000)
    ) + fadeOut()

val ThumbSize = 200.dp


@Preview
@Composable
fun TimelineScreenPreview() {
    TimelineScreen(selectedWonder = TajMahal, onClickBack = {})
}