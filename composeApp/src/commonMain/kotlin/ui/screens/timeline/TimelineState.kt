package ui.screens.timeline

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import models.AllTimelineEvents
import kotlin.math.roundToInt

const val StartYear = -3000
const val EndYear = 3000
const val TimelineDuration = EndYear - StartYear

/**
 * Range of year in which the event popup is shown and event marker is highlighted
 */
val Int.eventHighlightRange get(): IntRange = (this - 4..this + 4)

val MinZoomScale = 0.5f.dp
val MaxZoomScale = 2f.dp

@Composable
fun rememberTimelineState() = rememberSaveable(saver = TimelineState.Saver) {
    TimelineState(0)
}

/**
 * Contains a `ScrollState` and exposes scrolling related logic in terms of "Timeline Year".
 *
 * For example [currentYear] returns the year corresponding to the current scroll position.
 * Similarly [scrollToYear] can be used to scroll to a particular year without knowing its position in pixels.
 */
class TimelineState(initialScroll: Int = 0) {
    val scrollState = ScrollState(initialScroll)
    var scale by mutableStateOf(1.dp)
        private set

    val timelineHeight get() = TimelineDuration * scale

    val scrollFraction by derivedStateOf {
        ((scrollState.value) / scrollState.maxValue.toFloat()).coerceIn(0f, 1f)
    }

    val currentYear by derivedStateOf {
        (scrollFraction * TimelineDuration - 3000).roundToInt()
    }

    val currentYearHighlightRange get() = currentYear.eventHighlightRange

    val currentTimelineEvent by derivedStateOf {
        AllTimelineEvents.firstOrNull { it.year in currentYearHighlightRange }
    }

    fun setScale(zoom: Float) {
        scale = (scale * zoom).coerceIn(MinZoomScale, MaxZoomScale)
    }

    /**
     * Scrolls to the [year] starting from 1000 years above it.
     */
    suspend fun animateRevealYear(year: Int) {
        val startYear = (year - 1000).coerceAtLeast(StartYear)
        // first snap timeline to scroll pos just above the selected year
        scrollToYear(startYear)
        // then animate from that point till year appears on screen
        animateScrollToYear(
            year, spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = 25f
            )
        )
    }

    /**
     * Snaps to given [year]
     */
    private suspend fun scrollToYear(year: Int) {
        val scrollPositionPx = calculateScrollPosFromYear(year)
        scrollState.scrollTo(scrollPositionPx)
    }

    /**
     * Scroll to given [year] with animation
     */
    private suspend fun animateScrollToYear(
        year: Int,
        animationSpec: AnimationSpec<Float> = tween(1000)
    ) {
        val scrollPositionPx = calculateScrollPosFromYear(year)
        scrollState.animateScrollTo(scrollPositionPx, animationSpec)
    }

    /**
     * Returns scroll position in pixel from the [year]
     */
    private fun calculateScrollPosFromYear(year: Int): Int {
        val scrollFraction = (year - StartYear).toFloat() / TimelineDuration
        return (scrollFraction * scrollState.maxValue).toInt()
    }

    companion object {
        /**
         * This is used to save [TimelineState] in an event of Activity or Process recreation
         */
        val Saver = Saver<TimelineState, Int>(
            save = { it.scrollState.value },
            restore = { TimelineState(it) }
        )
    }
}