package ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.composables.AppIconButton
import ui.composables.LongButton
import ui.composables.WonderTitleText
import ui.flattenedImage
import ui.screens.timeline.components.SmallTimeline
import ui.screens.timeline.components.TimelineEventCard
import ui.theme.accent2
import ui.theme.black
import ui.theme.greyStrong
import ui.theme.white
import ui.utils.filePainterResource
import utils.StringUtils.getYrSuffix
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.tab_timeline

@OptIn(
    ExperimentalResourceApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun WonderEvents(
    wonder: Wonder,
    navigateToTimeline: () -> Unit,
) = BoxWithConstraints(Modifier.background(black).safeDrawingPadding()) {
    val wonderEvents = wonder.events

    val bgHeight = maxHeight * 0.55f
    val sheetHeight = maxHeight * 0.45f

    @Composable
    fun ListComposable(modifier: Modifier = Modifier) {
        LazyColumn(modifier) {
            items(wonderEvents.toList()) { item ->
                TimelineEventCard(
                    year = item.first,
                    text = item.second,
                    darkMode = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
            item {
                // TODO: implement hidden artifact
                Spacer(Modifier.height(500.dp))
            }
        }
    }

    @Composable
    fun NavigateToTimelineBtn() {
        LongButton(
            label = "OPEN GLOBAL TIMELINE",
            onClick = navigateToTimeline,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 200.dp)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        )
    }

    @Composable
    fun WonderImageWithTimeline() {
        Column(
            modifier = Modifier.height(bgHeight).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Wonder Image with Title
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    filePainterResource(wonder.flattenedImage),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 100,
                                topEndPercent = 100
                            )
                        )
                        .drawWithContent {
                            val gradient =
                                Brush.verticalGradient(listOf(Color.Transparent, black))
                            drawContent()
                            drawRect(gradient)
                        },
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    alignment = BiasAlignment(0f, -0.5f),
                )
                WonderTitleText(
                    wonder,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            SmallTimeline(
                highLightedWonder = wonder,
                modifier = Modifier
                    .height(80.dp).fillMaxWidth()
            )
        }
    }

    @Composable
    fun SingleColumnLayout() {
        BottomSheetScaffold(
            containerColor = black,
            sheetContainerColor = black,
            sheetPeekHeight = sheetHeight,
            sheetDragHandle = {
                BottomSheetDefaults.DragHandle(
                    shape = MaterialTheme.shapes.large,
                    height = 4.dp,
                    color = accent2
                )
            },
            sheetContent = {
                ListComposable()
            },
        ) {
            WonderImageWithTimeline()
        }
        Box(
            Modifier
                .zIndex(10f)
                .background(black)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            NavigateToTimelineBtn()
        }
    }

    @Composable
    fun TwoColumnLayout() {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WonderImageWithTimeline()
                NavigateToTimelineBtn()
            }

            ListComposable(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            )
        }
    }

    if (maxWidth > 600.dp) {
        TwoColumnLayout()
    } else {
        SingleColumnLayout()
    }

    // Top app bar remains common for both the layout
    TopAppBar(
        modifier = Modifier.align(Alignment.TopStart),
        title = {},
        actions = {
            AppIconButton(
                icon = Res.drawable.tab_timeline,
                contentDescription = "Open Timeline",
                onClick = navigateToTimeline
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )

}