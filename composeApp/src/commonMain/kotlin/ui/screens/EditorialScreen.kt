package ui.screens

import CompassDivider
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.composables.AppIconButton
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
import ui.photo1
import ui.photo2
import ui.photo3
import ui.photo4
import ui.screens.home.bgTexture
import ui.theme.B612Mono
import ui.theme.accent1
import ui.theme.bgColor
import ui.theme.fgColor
import ui.theme.quoteFont
import ui.theme.white
import ui.utils.filePainterResource
import utils.StringUtils
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.appBarTitleConstruction
import wonderouscompose.composeapp.generated.resources.appBarTitleFactsHistory
import wonderouscompose.composeapp.generated.resources.appBarTitleLocation
import wonderouscompose.composeapp.generated.resources.artifactDetailsLabelDate
import wonderouscompose.composeapp.generated.resources.construction
import wonderouscompose.composeapp.generated.resources.geography
import wonderouscompose.composeapp.generated.resources.history
import wonderouscompose.composeapp.generated.resources.icon_next
import wonderouscompose.composeapp.generated.resources.semanticsNext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.EditorialScreen(
    wonder: Wonder,
    openHomeScreen: () -> Unit,
    openMapScreen: (Wonder) -> Unit,
    openVideoScreen: (videoId: String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) = BoxWithConstraints {
    val maxWidth = maxWidth
    val density = LocalDensity.current
    val hapticFeedback = LocalHapticFeedback.current
    // Shows home screen when the user has over-scrolled enough to top
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            private var notified = false
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (available.y > 100 && !notified) {
                    notified = true
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    openHomeScreen()
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    val scrollState = rememberLazyListState()

    val entered = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        entered.value = true
    }

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
        val mainImageContainerHeight = 250.dp
        Box(Modifier.padding(top = 20.dp).height(mainImageContainerHeight).fillMaxWidth()) {
            Image(
                filePainterResource(wonder.getAssetPath(wonder.mainImageName)),
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = "image-${wonder.name}"),
                        animatedVisibilityScope
                    )
                    .align(wonder.mainImageAlignment)
                    .wrapContentHeight(Alignment.Top, true)
                    .requiredHeight(mainImageContainerHeight * wonder.mainImageFractionalHeight)
                    .zIndex(wonder.mainImageZIndex),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.BottomCenter,
            )
        }
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
                    HorizontalDivider(Modifier.weight(1f))
                    Text(
                        stringResource(wonder.subTitle),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.titleSmall,
                        color = onPrimaryColor
                    )
                    HorizontalDivider(Modifier.weight(1f))
                }
                WonderTitleText(
                    wonder,
                    Modifier.sharedBounds(
                        rememberSharedContentState(key = wonder.name),
                        animatedVisibilityScope,
                        zIndexInOverlay = 1f
                    )
                )
                Text(
                    stringResource(wonder.regionTitle),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = onPrimaryColor
                )
                CompassDivider(
                    Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                    isExpanded = entered.value && scrollState.firstVisibleItemScrollOffset < 100
                )
                Text(
                    stringResource(
                        Res.string.artifactDetailsLabelDate,
                        StringUtils.formatYr(wonder.startYr),
                        StringUtils.formatYr(wonder.endYr)
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
                AnimatedVisibility(
                    entered.value,
                    enter = fadeIn(tween(durationMillis = 1500, delayMillis = 400))
                ) {
                    Image(
                        filePainterResource(wonder.photo1),
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
                stringResource(wonder.historyInfo1),
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
                bgImage = wonder.photo2,
                pullQuote1Top = stringResource(wonder.pullQuote1Top),
                pullQuote1Bottom = stringResource(wonder.pullQuote1Bottom),
                pullQuote1Progress = pullQuote1Progress,
            )
        }
        // 5
        surfaceItem {
            Column {
                CallOut(
                    stringResource(wonder.callout1)
                )
                InfoText(
                    stringResource(wonder.historyInfo2),
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
            InfoText(stringResource(wonder.constructionInfo1))
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
                    text = stringResource(wonder.videoCaption),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        // 9
        surfaceItem {
            CallOut(stringResource(wonder.callout2))
        }
        // 10
        surfaceItem {
            InfoText(stringResource(wonder.constructionInfo2))
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
                topImagePath = wonder.photo3,
                bottomImagePath = wonder.photo4,
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
            InfoText(stringResource(wonder.locationInfo1))
        }
        // 14
        surfaceItem {
            Quote(
                text = stringResource(wonder.pullQuote2),
                author = stringResource(wonder.pullQuote2Author),
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 24.dp)
            )
        }
        // 15
        surfaceItem {
            InfoText(stringResource(wonder.locationInfo2))
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
                    latLng = wonder.latLng,
                    title = wonder.mapCaption?.let { stringResource(it) } ?: "",
                    zoomLevel = .05f,
                    mapType = MapType.Normal,
                )
            }
        }
    }

    AnimatedVisibility(
        visible = scrollState.firstItemScrollProgress < 0.6f,
        modifier = Modifier.safeDrawingPadding().align(Alignment.TopEnd)
    ) {
        AppIconButton(
            Res.drawable.icon_next,
            contentDescription = stringResource(Res.string.semanticsNext),
            onClick = openHomeScreen,
            modifier = Modifier
                .rotate(-90f)
                .padding(16.dp)
        )
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
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        modifier = modifier.padding(horizontal = 16.dp),
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp)
    )
}

/**
 * Top rotating title to indicate current section
 */
@Composable
fun InfoTitle(
    wonderColor: Color,
    infoSection: InfoSection,
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
                    // TODO: use grapheme splitting
                    text = stringResource(infoSection.title).toCharArray()
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
                    painterResource(it.imageDrawable),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun CallOut(
    text: String,
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
            style = MaterialTheme.typography.quoteFont,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.quoteFont.copy(fontWeight = FontWeight.W400),
            modifier = Modifier.padding(vertical = 32.dp)
        )
        Text(
            "- $author",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


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
            filePainterResource(bgImage),
            modifier = Modifier.fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.secondary, shape)
                .padding(8.dp)
                .alpha(pullQuote1Progress * 2 + 0.4f)
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
            val textStyle = MaterialTheme.typography.quoteFont.copy(
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
            filePainterResource(topImagePath),
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
            filePainterResource(bottomImagePath),
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

@Composable
fun AnimatedEntry(
    delay: Duration = 0.milliseconds,
    entry: EnterTransition = fadeIn(),
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val entered = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(delay)
        entered.value = true
    }
    AnimatedVisibility(
        visible = entered.value,
        modifier = modifier,
        enter = entry
    ) {
        content()
    }
}


enum class InfoSection(val title: StringResource, val imageDrawable: DrawableResource) {
    FactsAndHistory(Res.string.appBarTitleFactsHistory, Res.drawable.history),
    Construction(Res.string.appBarTitleConstruction, Res.drawable.construction),
    Location(Res.string.appBarTitleLocation, Res.drawable.geography)
}

private val Wonder.cutoutShape
    get() = when (this) {
        ChichenItza ->
            CutCornerShape(topStartPercent = 40, topEndPercent = 40)

        PyramidsGiza, MachuPicchu ->
            CutCornerShape(topStartPercent = 50, topEndPercent = 50)

        ChristRedeemer -> RoundedCornerShape(topStartPercent = 60, topEndPercent = 60)

        else -> RoundedCornerShape(topStartPercent = 80, topEndPercent = 80)
    }


private val Wonder.mainImageFractionalHeight
    get() = when (this) {
        ChichenItza -> 0.6f
        ChristRedeemer -> 2f
        Colosseum -> 0.9f
        GreatWall -> 0.9f
        MachuPicchu -> 0.8f
        Petra -> 0.8f
        PyramidsGiza -> 0.9f
        TajMahal -> 0.8f
    }

private val Wonder.mainImageZIndex
    get() = when (this) {
        ChristRedeemer -> -0.1f
        else -> 0.1f
    }

private val Wonder.mainImageAlignment
    get() = when (this) {
        ChristRedeemer -> Alignment.TopCenter
        else -> Alignment.BottomCenter
    }
