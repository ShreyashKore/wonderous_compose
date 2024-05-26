package ui.screens

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import models.AllTimeLineEvents
import kotlin.math.roundToInt

const val StartYear = -3000
const val EndYear = 3000
const val TimelineDuration = EndYear - StartYear

/**
 * Range of year in which the event popup is shown and event marker is highlighted
 */
val Int.eventHighlightRange get() = (this - 4..this + 4)

val MinZoomScale = 0.5f.dp
val MaxZoomScale = 2f.dp

class TimelineState {
    val scrollState = ScrollState(0)
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
        AllTimeLineEvents.firstOrNull { it.year in currentYearHighlightRange }
    }

    fun setScale(zoom: Float) {
        scale = (scale * zoom).coerceIn(MinZoomScale, MaxZoomScale)
    }

    /**
     * First snaps 1000 years above and then animates from there to the selected year
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

    private suspend fun scrollToYear(year: Int) {
        val scrollPositionPx = calculateScrollPosFromYear(year)
        debugPrint("scrolling to year $year $scrollPositionPx")
        scrollState.scrollTo(scrollPositionPx)
    }

    private suspend fun animateScrollToYear(
        year: Int,
        animationSpec: AnimationSpec<Float> = tween(1000)
    ) {
        val scrollPositionPx = calculateScrollPosFromYear(year)
        debugPrint("animate scrolling to $year $scrollPositionPx")
        scrollState.animateScrollTo(scrollPositionPx, animationSpec)
    }

    private fun calculateScrollPosFromYear(year: Int): Int {
        val scrollFraction = (year - StartYear).toFloat() / TimelineDuration
        return (scrollFraction * scrollState.maxValue).toInt()
    }
}