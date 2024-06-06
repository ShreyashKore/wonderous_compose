package ui.screens.timeline.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import models.Wonder
import ui.composables.RotatedLayout
import ui.screens.timeline.TimelineTracksLayout
import ui.theme.accent2
import ui.theme.offWhite


@Composable
fun SmallTimeline(
    getScrollFraction: (() -> Float)? = null,
    scrollThumbWidth: Dp = 72.dp,
    modifier: Modifier = Modifier,
    highLightedWonder: Wonder? = null,
) = RotatedLayout(rotationDegrees = -90f, modifier = modifier) {

    BoxWithConstraints {
        TimelineTracksLayout(
            modifier = Modifier.padding(12.dp),
        ) { wonder ->
            val shape = RoundedCornerShape(percent = 100)
            val bgColor = if (highLightedWonder == wonder) accent2 else Color.Transparent
            // A simple rounded box with border is shown in small timeline
            Box(
                Modifier.fillMaxSize().background(bgColor, shape).border(1.dp, accent2, shape)
            )
        }
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
