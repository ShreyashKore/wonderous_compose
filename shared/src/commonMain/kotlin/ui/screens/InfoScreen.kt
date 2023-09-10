package ui.screens

import CompassDivider
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import models.ChichenItza
import models.ChristRedeemer
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.composables.MapView
import ui.composables.YouTubeThumbnail
import ui.composables.firstItemScrollProgress
import ui.composables.scrollProgressFor
import ui.theme.Cinzel
import utils.StringUtils

private val maxImageHeight = 400.dp
private val minImageHeight = 52.dp

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class, ExperimentalStdlibApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun InfoScreen(
    wonder: Wonder
) {
    val scrollState = rememberLazyListState()
    val infoTitle by derivedStateOf {
        when (scrollState.firstVisibleItemIndex) {
            in 0..<5 -> InfoSection.FactsAndHistory
            in 5..<10 -> InfoSection.Construction
            else -> InfoSection.Location
        }
    }

    val titleAlpha by derivedStateOf {
        1 - scrollState.firstItemScrollProgress
    }

    val titleTranslationY by derivedStateOf {
        scrollState.firstItemScrollProgress * 300
    }

    val imageHeight by derivedStateOf {
        minImageHeight + (maxImageHeight - minImageHeight) * (1.5f - scrollState.scrollProgressFor(
            1
        ))
    }

    val imageAlpha by derivedStateOf {
        1.6f - scrollState.scrollProgressFor(1)
    }

    val pullQuote1Progress by derivedStateOf {
        (0.45f - scrollState.scrollProgressFor(4)).coerceAtLeast(0f)
    }

    val slidingImageProgress by derivedStateOf {
        scrollState.scrollProgressFor(10)
    }

    Scaffold(
        topBar = {
//            Text(
//                "VH Size : ${scrollState.layoutInfo.viewportSize}\n" +
//                        "Image H : $imageHeight\n" +
//                        "PP 0 : ${pullQuote1TopOffset}\n" +
//                        "SS 0 : ${scrollState.scrollProgressFor(0)}\n" +
//                        "SS 1 : ${scrollState.scrollProgressFor(1)}\n" +
//                        "SS 2 : ${scrollState.scrollProgressFor(2)}\n" +
//                        "SS 3 : ${scrollState.scrollProgressFor(3)}\n"
//            )
        }
    ) {
        val bgTransition = scrollState.firstItemScrollProgress
        Column(
            Modifier
                .background(SolidColor(wonder.bgColor), alpha = bgTransition)
        ) {
            Image(
                painterResource("images/chichen_itza/chichen.png"),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .zIndex(0.1f)
                    .graphicsLayer {
                        scaleX = 1.6f
                        scaleY = 1.6f
                        translationY = -100.dp.toPx()
                    },
                contentDescription = null,
                alignment = Alignment.BottomCenter,
            )
            Box(
                Modifier
                    .scale(1.2f)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(wonder.bgColor)
            )
        }
        LazyColumn(state = scrollState) {
            // 0
            item {
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
                    Text(
                        wonder.title,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = onPrimaryColor
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
                Box(Modifier.height(maxImageHeight)) {
                    Image(
                        painterResource("images/chichen_itza/photo-1.jpg"),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(imageHeight)
                            .alpha(imageAlpha)
                            .clip(RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }
            // 2
            stickyHeader {
                InfoTitle(
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
                Box(
                    Modifier.padding(16.dp)
                ) {
                    val shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)
                    Image(
                        painterResource("images/chichen_itza/photo-2.jpg"),
                        modifier = Modifier.fillMaxWidth()
                            .height(500.dp)
                            .border(1.dp, Color.Blue, shape)
                            .padding(8.dp)
                            .alpha(pullQuote1Progress * 2 + 0.6f)
                            .clip(shape),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.primaryContainer,
                            BlendMode.Color
                        )
                    )
                    Column(
                        Modifier.height(500.dp).fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val textStyle = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 36.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            wonder.pullQuote1Top,
                            style = textStyle,
                            modifier = Modifier.graphicsLayer {
                                translationY =
                                    pullQuote1Progress * -1000
                            }
                        )
                        Text(
                            wonder.pullQuote1Bottom,
                            style = textStyle,
                        )
                    }
                }
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
                Column {
                    YouTubeThumbnail(wonder.videoId, wonder.videoCaption)
                    CallOut(wonder.callout2)
                }
            }
            // 9
            surfaceItem {
                InfoText(wonder.constructionInfo2)
            }
            // 10
            surfaceItem {
                Box(Modifier.padding(16.dp).fillMaxWidth().height(700.dp)) {
                    Image(
                        painterResource("images/chichen_itza/photo-2.jpg"),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.TopEnd)
                            .zIndex(1f).height(400.dp)
                            .fillMaxWidth(0.8f)
                            .graphicsLayer {
                                translationY = slidingImageProgress * 500
                            }.clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
                    )
                    Image(
                        painterResource("images/chichen_itza/photo-2.jpg"),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomStart)
                            .zIndex(1f)
                            .graphicsLayer {
                                translationY = -(slidingImageProgress * 400)
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
            // 11
            surfaceItem {
                CompassDivider(
                    Modifier.padding(horizontal = 16.dp, vertical = 72.dp),
                    isExpanded = scrollState.firstVisibleItemIndex == 10
                )
            }
            // 12
            surfaceItem {
                InfoText(wonder.locationInfo1)
            }
            // 13
            surfaceItem {
                Quote(
                    text = wonder.pullQuote2,
                    author = wonder.pullQuote2Author,
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 24.dp)
                )
            }
            // 14
            surfaceItem {
                InfoText(wonder.locationInfo2)
            }
            // 15
            surfaceItem {
                // Map
                MapView(
                    modifier = Modifier
                        .padding(top = 12.dp, start = 12.dp, bottom = 200.dp, end = 12.dp)
                        .height(320.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    gps = wonder.gps,
                    title = "Map",
                    parentScrollEnableState = mutableStateOf(true),
                )
            }
        }
    }
}

fun LazyListScope.surfaceItem(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    item {
        Box(
            modifier.background(MaterialTheme.colorScheme.surface).fillMaxWidth()
        ) {
            content()
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


@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class)
@Composable
fun InfoTitle(
    infoSection: InfoSection
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(Modifier.background(MaterialTheme.colorScheme.surface).height(42.dp).fillMaxWidth())

        Column(
            Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStartPercent = 100, topEndPercent = 100),
                ).padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val tweenForInfo = tween<IntOffset>(durationMillis = 500)
            val tweenForTitle = tween<Float>(durationMillis = 500)
            AnimatedContent(
                targetState = infoSection,
                transitionSpec = {
                    val c = initialState.compareTo(targetState)
                    slideIn(tweenForInfo) { IntOffset(it.width * c, 0) } with
                            slideOut(tweenForInfo) { IntOffset(-it.width * c, 0) }
                },
            ) {
                Text(it.title, textAlign = TextAlign.Center, fontSize = 20.sp)
            }
            Spacer(Modifier.height(12.dp))
            AnimatedContent(
                targetState = infoSection,
                transitionSpec = {
                    scaleIn(tweenForTitle) with scaleOut(tweenForTitle)
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


enum class InfoSection(val title: String, val imageName: String) {
    FactsAndHistory("Facts & History", "history.png"),
    Construction("Construction", "construction.png"),
    Location("Location", "geography.png")
}

val InfoSection.imagePath get() = "images/common/$imageName"


val Wonder.bgColor
    get() = when (this) {
        ChichenItza -> Color.fromHex("#14341C")
        ChristRedeemer -> Color.fromHex("#2B6961")
    }


fun Color.Companion.fromHex(hex: String) = Color(
    ("ff" + hex.removePrefix("#").lowercase()).toLong(16)
)