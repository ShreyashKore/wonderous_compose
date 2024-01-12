package ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ImagePaths
import ui.composables.AppIconButton
import ui.composables.LongButton
import ui.composables.WonderTitleText
import ui.flattened
import ui.theme.TenorSans
import ui.theme.accent2
import ui.theme.black
import ui.theme.greyStrong
import ui.theme.white
import utils.StringUtils.getYrSuffix
import kotlin.math.absoluteValue

@OptIn(
    ExperimentalResourceApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun WonderEvents(
    wonder: Wonder,
    navigateToTimeLine: () -> Unit,
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
            onClick = navigateToTimeLine,
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
                    painterResource(wonder.flattened),
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
            SmallTimeLine(
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
                iconPath = "${ImagePaths.common}/tab-timeline.png",
                contentDescription = "Open Timeline",
                onClick = navigateToTimeLine
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )

}


@Composable
fun TimelineEventCard(
    year: Int, text: String,
    darkMode: Boolean = false,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (darkMode) greyStrong else white
    val contentColor = if (darkMode) white else black

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(backgroundColor, contentColor),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date
            Column(
                modifier = Modifier
                    .width(75.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "${year.absoluteValue}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.W500,
                        lineHeight = 20.sp,
                        fontFamily = TenorSans
                    )
                )
                Text(
                    text = getYrSuffix(year),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = TenorSans,
                        fontWeight = FontWeight.W500
                    )
                )
            }

            // Divider
            Divider(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(1.dp)
                    .fillMaxHeight(),
                color = contentColor
            )

            // Text content
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 20.sp
                )
            )
        }
    }

}