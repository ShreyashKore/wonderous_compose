package ui.screens

import CompassDivider
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import models.ChichenItza
import models.ChristRedeemer
import models.MachuPicchu
import models.PyramidsGiza
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.composables.BackgroundTexture
import ui.composables.CircularText
import ui.composables.MapType
import ui.composables.MapView
import ui.composables.WonderTitleText
import ui.composables.YouTubeThumbnail
import ui.composables.firstItemScrollProgress
import ui.composables.scrollProgressFor
import ui.getAssetPath
import ui.mainImageName
import ui.screens.home.bgTexture
import ui.theme.B612Mono
import ui.theme.Cinzel
import ui.theme.accent1
import ui.theme.bgColor
import ui.theme.fgColor
import ui.theme.white
import utils.StringUtils


@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class, ExperimentalStdlibApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun EditorialScreen(
    wonder: Wonder,
    openHomeScreen: () -> Unit,
    openMapScreen: (Wonder) -> Unit,
    openVideoScreen: (videoId: String) -> Unit,
) = BoxWithConstraints {
    val maxWidth = maxWidth
    val density = LocalDensity.current
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 15 && source == NestedScrollSource.Drag) {
                    // We are detecting overscroll on top
                    openHomeScreen()
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    val scrollState = rememberLazyListState()

    // background and hero image
    Box(
        Modifier
            .background(wonder.fgColor)
            .drawWithContent {
                val bgAlpha = scrollState.firstItemScrollProgress
                drawContent()
                drawRect(SolidColor(wonder.bgColor), alpha = bgAlpha)
            }
            .safeDrawingPadding()
    ) {
        Column {
            BackgroundTexture(
                texture = wonder.bgTexture,
                alpha = 0.3f,
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
            Box(
                Modifier
                    .scale(1.2f)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(wonder.bgColor)
            )
        }
        Image(
            painterResource(wonder.getAssetPath(wonder.mainImageName)),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 10.dp)
                .zIndex(0.1f),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.BottomCenter,
        )
    }

    // Main content
    LazyColumn(
        modifier = Modifier
            .safeDrawingPadding()
            .nestedScroll(nestedScrollConnection),
        state = scrollState,
    ) {
        // 0
        item {
            val titleAlpha by remember {
                derivedStateOf {
                    1 - scrollState.firstItemScrollProgress
                }
            }

            val titleTranslationY by remember {
                derivedStateOf {
                    scrollState.firstItemScrollProgress * 300
                }
            }

            Column(
                Modifier.fillMaxWidth()
                    .padding(top = 300.dp, bottom = 16.dp)
                    .zIndex(0.1f)
                    .graphicsLayer {
                        alpha = titleAlpha
                        translationY = titleTranslationY
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(Modifier.weight(1f))
                    Text(
                        wonder.subTitle,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.titleSmall,
                        color = onPrimaryColor
                    )
                    Divider(Modifier.weight(1f))
                }
                WonderTitleText(
                    wonder,
                )
                Text(
                    wonder.regionTitle,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = onPrimaryColor
                )
                CompassDivider(
                    Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                    isExpanded = scrollState.firstVisibleItemScrollOffset < 100
                )
                Text(
                    StringUtils.formatYr(wonder.startYr) + " to " + StringUtils.formatYr(
                        wonder.endYr
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    color = onPrimaryColor
                )
            }
        }
        // 1
        item {
            val maxImageHeight = minOf(maxWidth, 800.dp)
            val minImageHeight = minOf(maxImageHeight * .1f, 50.dp)
            val imageAlpha by remember {
                derivedStateOf {
                    1.6f - scrollState.scrollProgressFor(1)
                }
            }
            val imageWidth = minOf(maxWidth, 800.dp)
            val imageHeight by remember {
                derivedStateOf {
                    when (scrollState.firstVisibleItemIndex) {
                        in 0..<1 -> maxImageHeight
                        1 -> {
                            val scrollOffsetDp = with(density) {
                                scrollState.firstVisibleItemScrollOffset.toDp()
                            }
                            (maxImageHeight - scrollOffsetDp).coerceAtLeast(minImageHeight)
                        }

                        else -> minImageHeight
                    }
                }
            }

            Box(
                Modifier.height(maxImageHeight).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(wonder.getAssetPath("photo-1.jpg")),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .height(imageHeight)
                        .width(imageWidth)
                        .alpha(imageAlpha)
                        .clip(wonder.cutoutShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
            }
        }
        // 2
        stickyHeader {
            val infoTitle by remember {
                derivedStateOf {
                    when (scrollState.firstVisibleItemIndex) {
                        in 0..<5 -> InfoSection.FactsAndHistory
                        in 5..<10 -> InfoSection.Construction
                        else -> InfoSection.Location
                    }
                }
            }

            InfoTitle(
                wonderColor = wonder.bgColor,
                infoSection = infoTitle
            )
        }
        // 3
        surfaceItem {
            InfoText(
                wonder.historyInfo1,
                Modifier.padding(top = 16.dp, bottom = 24.dp)
            )
        }
        // 4
        surfaceItem {
            val pullQuote1Progress by remember {
                derivedStateOf {
                    (0.45f - scrollState.scrollProgressFor(4)).coerceIn(0f, .2f)
                }
            }

            PullQuote1(
                bgImage = wonder.getAssetPath("photo-2.jpg"),
                pullQuote1Top = wonder.pullQuote1Top,
                pullQuote1Bottom = wonder.pullQuote1Bottom,
                pullQuote1Progress = pullQuote1Progress,
            )
        }
        // 5
        surfaceItem {
            Column {
                CallOut(
                    wonder.callout1
                )
                InfoText(
                    wonder.historyInfo2,
                    Modifier.padding(top = 16.dp)
                )
            }

        }
        // 6
        surfaceItem {
            CompassDivider(
                Modifier.padding(horizontal = 16.dp, vertical = 72.dp),
                isExpanded = scrollState.firstVisibleItemIndex in 4..5
            )
        }
        // 7
        surfaceItem {
            InfoText(wonder.constructionInfo1)
        }
        // 8
        surfaceItem {
            Column(
                Modifier
                    .padding(vertical = 48.dp)
                    .widthIn(max = 450.dp)
            ) {
                YouTubeThumbnail(
                    wonder.videoId,
                    onClick = { openVideoScreen(wonder.videoId) },
                    modifier = Modifier
                        .clickable {
                            openVideoScreen(wonder.videoId)
                        }
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = wonder.videoCaption,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        // 9
        surfaceItem {
            CallOut(wonder.callout2)
        }
        // 10
        surfaceItem {
            InfoText(wonder.constructionInfo2)
        }
        // 11
        surfaceItem {
            val parallaxProgress by remember {
                derivedStateOf {
                    scrollState.scrollProgressFor(11)
                }
            }
            ParallaxImages(
                parallaxProgress = parallaxProgress,
                topImagePath = wonder.getAssetPath("photo-3.jpg"),
                bottomImagePath = wonder.getAssetPath("photo-4.jpg"),
            )
        }
        // 12
        surfaceItem {
            CompassDivider(
                Modifier.padding(horizontal = 16.dp, vertical = 72.dp),
                isExpanded = scrollState.firstVisibleItemIndex == 11
            )
        }
        // 13
        surfaceItem {
            InfoText(wonder.locationInfo1)
        }
        // 14
        surfaceItem {
            Quote(
                text = wonder.pullQuote2,
                author = wonder.pullQuote2Author,
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 24.dp)
            )
        }
        // 15
        surfaceItem {
            InfoText(wonder.locationInfo2)
        }
        // 16
        surfaceItem {
            // Map
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = {
                        openMapScreen(wonder)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .background(white, RoundedCornerShape(100f)),
                ) {
                    Icon(
                        Icons.Rounded.Place,
                        contentDescription = null,
                    )
                }
                MapView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 12.dp, bottom = 200.dp, end = 12.dp)
                        .height(320.dp)
                        .widthIn(max = 450.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    gps = wonder.gps,
                    title = "Map",
                    zoomLevel = .05f,
                    mapType = MapType.Normal,
                )
            }
        }
    }
}

/**
 * Helper [LazyListScope] item with background color applied as surface
 */
fun LazyListScope.surfaceItem(
    modifier: Modifier = Modifier,
    maxContentWidth: Dp = 620.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    item {
        Box(
            modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.widthIn(max = maxContentWidth)) {
                content()
            }
        }
    }
}

@Composable
fun InfoText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        modifier = modifier.padding(horizontal = 16.dp),
        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp)
    )
}

/**
 * Top rotating title to indicate current section
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class)
@Composable
fun InfoTitle(
    wonderColor: Color,
    infoSection: InfoSection
) {
    val tween = tween<Float>(durationMillis = 1000)
    val circularTextAngle by animateFloatAsState(
        when (infoSection) {
            InfoSection.FactsAndHistory -> 0f
            InfoSection.Construction -> 360f
            InfoSection.Location -> 720f
        },
        animationSpec = tween
    )

    Box(
        modifier = Modifier.fillMaxWidth().background(wonderColor),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(Modifier.background(MaterialTheme.colorScheme.surface).height(42.dp).fillMaxWidth())

        Box(
            Modifier
                .height(120.dp)
                .width(240.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100),
                ),
            contentAlignment = Alignment.Center,
        ) {

            AnimatedContent(
                modifier = Modifier
                    .clipToBounds() // Clipper for CircularText
                    .padding(top = 20.dp).layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeable.placeRelative(
                                (constraints.maxWidth - placeable.width) / 2,
                                constraints.maxHeight / 2 - placeable.height / 2 + 40.dp.roundToPx(),
                                0f
                            )
                        }
                    }.wrapContentSize(unbounded = true),
                targetState = infoSection,
                transitionSpec = { fadeIn(tween) togetherWith fadeOut(tween) },
            ) { infoSection ->
                CircularText(
                    text = infoSection.title.toCharArray()
                        .map { it.toString() }, // Circular Text needs individual letters (graphemes)
                    radius = 90.dp,
                    textStyle = TextStyle(fontSize = 16.sp, fontFamily = B612Mono, color = accent1),
                    modifier = Modifier.padding(20.dp).size(200.dp).rotate(circularTextAngle)
                )
            }
            AnimatedContent(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
                targetState = infoSection,
                transitionSpec = {
                    scaleIn(tween) togetherWith scaleOut(tween)
                }
            ) {
                Image(
                    painterResource(it.imagePath),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun CallOut(
    text: String
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Box(
            Modifier
                .padding(end = 16.dp)
                .background(MaterialTheme.colorScheme.primary)
                .width(2.dp).fillMaxHeight()
        )
        Text(
            text,
            style = TextStyle(fontStyle = FontStyle.Italic)
        )
    }
}


@Composable
private fun Quote(
    text: String,
    author: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "“",
            fontSize = 120.sp,
            modifier = Modifier.height(64.dp),
            fontFamily = Cinzel,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text, fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        Text(
            author,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PullQuote1(
    bgImage: String,
    pullQuote1Top: String,
    pullQuote1Bottom: String,
    pullQuote1Progress: Float,
) {
    Box(
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 48.dp)
            .widthIn(max = 450.dp)
            .height(500.dp)
    ) {
        val shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)
        Image(
            painterResource(bgImage),
            modifier = Modifier.fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.secondary, shape)
                .padding(8.dp)
                .alpha(pullQuote1Progress * 2 + 0.6f)
                .clip(shape),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                Color(0xFFBEABA1),
                BlendMode.ColorBurn
            )
        )
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val textStyle = MaterialTheme.typography.displaySmall.copy(
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )
            Text(
                pullQuote1Top,
                style = textStyle,
                modifier = Modifier.graphicsLayer {
                    translationY =
                        pullQuote1Progress * -1000
                }
            )
            Text(
                pullQuote1Bottom,
                style = textStyle,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ParallaxImages(
    parallaxProgress: Float,
    topImagePath: String,
    bottomImagePath: String,
) {

    Box(
        Modifier
            .padding(16.dp)
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .height(700.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painterResource(topImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.align(Alignment.TopEnd)
                .zIndex(1f).height(400.dp)
                .fillMaxWidth(0.8f)
                .graphicsLayer {
                    translationY = parallaxProgress * 500
                }.clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
        )
        Image(
            painterResource(bottomImagePath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.align(Alignment.BottomStart)
                .zIndex(1f)
                .graphicsLayer {
                    translationY = -(parallaxProgress * 400)
                }.clip(
                    RoundedCornerShape(
                        bottomStartPercent = 100,
                        bottomEndPercent = 100
                    )
                ).height(300.dp)
                .fillMaxWidth(0.6f)
        )
    }
}

enum class InfoSection(val title: String, val imageName: String) {
    FactsAndHistory("FACTS & HISTORY", "history.png"),
    Construction("CONSTRUCTION", "construction.png"),
    Location("LOCATION", "geography.png")
}

val InfoSection.imagePath get() = "images/common/$imageName"

private val Wonder.cutoutShape
    get() = when (this) {
        ChichenItza ->
            CutCornerShape(topStartPercent = 40, topEndPercent = 40)

        PyramidsGiza, MachuPicchu ->
            CutCornerShape(topStartPercent = 50, topEndPercent = 50)

        ChristRedeemer -> RoundedCornerShape(topStartPercent = 60, topEndPercent = 60)

        else -> RoundedCornerShape(topStartPercent = 80, topEndPercent = 80)
    }