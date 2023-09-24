package ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.moriatsushi.insetsx.ExperimentalSoftwareKeyboardApi
import com.moriatsushi.insetsx.safeDrawing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleAppBar(
    title: String,
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.(Float) -> Unit,
    fab: @Composable (() -> Unit)? = null,
    onBack: () -> Unit = {},
    expandedHeight: Dp = Dimensions.topBarExpandedHeight,
    collapsedHeight: Dp = Dimensions.topBarHeight,
    scrimColor: Color = MaterialTheme.colorScheme.surface,
    scrollBehaviour: TopAppBarScrollBehavior
) {
    CollapsibleAppBarLayout(
        modifier = modifier,
        content = { scrollFraction ->
            Row(modifier = Modifier.weight(1f)) {
                val fontSizeSmall = 24f
                val fontSizeBig = 36f
                val fontSize =
                    (fontSizeBig - (scrollFraction * (fontSizeBig - fontSizeSmall))).sp

                val fullPadding = 64.dp
                val collapsedPadding = 24.dp
                val padding =
                    collapsedPadding + (scrollFraction * (fullPadding - collapsedPadding).value).dp

                Text(
                    text = title,
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp,
                            start = padding,
                            end = 24.dp
                        )
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = fontSize,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        background = { scrollFraction ->

            background(scrollFraction)
            // Scrim
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = scrimColor.copy(scrollFraction))
            )

            BackButton(
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.TopStart),
                onClick = onBack
            )
        },
        foreground = { scroll ->
            AnimatedVisibility(
                modifier = Modifier.offset(y = 52.dp),
                visible = scroll > 0.5
            ) {
                fab?.invoke()

            }
        },
        expandedHeight = expandedHeight,
        collapsedHeight = collapsedHeight,
        scrollBehaviour = scrollBehaviour
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSoftwareKeyboardApi::class)
@Composable
fun CollapsibleAppBarLayout(
    modifier: Modifier = Modifier,
    expandedHeight: Dp = Dimensions.topBarExpandedHeight,
    collapsedHeight: Dp = Dimensions.topBarHeight,
    scrollBehaviour: TopAppBarScrollBehavior,
    content: @Composable BoxScope.(Float) -> Unit,
) {

    val expandedHeightPx: Int
    val collapsedHeightPx: Int
    with(LocalDensity.current) {
        expandedHeightPx = expandedHeight.toPx().toInt()
        collapsedHeightPx = collapsedHeight.toPx().toInt()
    }

    val scrollFraction =
        (-scrollBehaviour.state.contentOffset / (expandedHeightPx - collapsedHeightPx))
            .coerceIn(0f..1f)

    val height by derivedStateOf {
        expandedHeight - (scrollFraction * (expandedHeight - collapsedHeight).value).dp
    }

    Box(
        modifier = modifier.zIndex(1f).height(height).windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        content(scrollFraction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleAppBarLayout(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.(Float) -> Unit,
    background: @Composable BoxScope.(Float) -> Unit = { _ -> },
    foreground: @Composable (BoxScope.(Float) -> Unit)? = null,
    expandedHeight: Dp = Dimensions.topBarExpandedHeight,
    collapsedHeight: Dp = Dimensions.topBarHeight,
    scrollBehaviour: TopAppBarScrollBehavior,
) {
    CollapsibleAppBarLayout(
        modifier,
        expandedHeight,
        collapsedHeight,
        scrollBehaviour,
    ) { scrollFraction ->
        val fullElevation = 8.dp
        val zeroElevation = 0.dp
        val elevation by derivedStateOf {
            zeroElevation + fullElevation * scrollFraction
        }
        Column {
            Spacer(
                modifier = Modifier
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
            ) {

                background(scrollFraction)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeightIn(min = Dimensions.topBarHeight),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        content(scrollFraction)
                    }
                }

                if (foreground != null)
                    foreground(scrollFraction)

            }
        }
    }
}