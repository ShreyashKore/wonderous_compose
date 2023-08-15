package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.composables.MapView
import ui.composables.firstItemScrollProgress
import ui.composables.scrollProgressFor

private val maxImageHeight = 400.dp
private val minImageHeight = 52.dp

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class, ExperimentalStdlibApi::class
)
@Composable
fun InfoScreen(
    wonder: Wonder
) {
    val scrollState = rememberLazyListState()
    val infoTitle = derivedStateOf {
        when (scrollState.firstVisibleItemIndex) {
            in 0..<5 -> InfoSection.FactsAndHistory
            in 5..<7 -> InfoSection.Construction
            else -> InfoSection.Location
        }
    }

    val titleAlpha by derivedStateOf {
        1 - (scrollState.firstItemScrollProgress * 1.5f).coerceAtMost(1f)
    }

    val titleTranslationY by derivedStateOf {
        scrollState.firstItemScrollProgress * 200
    }

    val imageHeight by derivedStateOf {
        minImageHeight + (maxImageHeight - minImageHeight) * (1 - scrollState.scrollProgressFor(1))
    }

    val imageAlpha by derivedStateOf {
        1.4f - scrollState.scrollProgressFor(1)
    }

    Scaffold {
        Column {
            Image(
                painterResource("images/chichen_itza/chichen.png"),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .zIndex(0.1f)
                    .graphicsLayer {
                        scaleX = 1.6f
                        scaleY = 1.6f
                        translationY = -50.dp.toPx()
                    },
                contentDescription = null,
                alignment = Alignment.BottomCenter,
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Green)
            )
        }
        LazyColumn(state = scrollState) {
            item {
                Spacer(modifier = Modifier.height(350.dp))
                Column(
                    Modifier.fillMaxWidth().zIndex(0.1f).graphicsLayer {
                        alpha = titleAlpha
                        translationY = titleTranslationY
                    },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Divider(Modifier.padding(8.dp).weight(1f))
                        Text(
                            wonder.subTitle,
                            style = TextStyle()
                        )
                        Divider(Modifier.padding(8.dp).weight(1f))
                    }
                    Text(
                        wonder.title,
                        style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center)
                    )
                    Text(
                        wonder.regionTitle,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
            stickyHeader {
                InfoTitle(
                    infoSection = infoTitle
                )
            }
            item {
                InfoText(
                    wonder.historyInfo1,
                    Modifier.padding(top = 16.dp, bottom = 24.dp)
                )
            }
            item {
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
                            .clip(shape),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        Modifier.height(800.dp).fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val textStyle = TextStyle(fontSize = 42.sp, textAlign = TextAlign.Center)
                        Text(
                            wonder.pullQuote1Top,
                            style = textStyle,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            wonder.pullQuote1Bottom,
                            style = textStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            item {
                InfoText(
                    wonder.historyInfo2,
                    Modifier.padding(top = 16.dp)
                )
            }
            item {
                AnimatedDivider()
            }
            item {
                InfoText(wonder.constructionInfo1)
            }
            item {
                InfoText(wonder.constructionInfo2)
            }
            item {
                AnimatedDivider()
            }
            item {
                InfoText(wonder.locationInfo1)
            }
            item {
                InfoText(wonder.locationInfo2)
            }
            item {
                // Map
                MapView(
                    modifier = Modifier.padding(12.dp).height(360.dp),
                    gps = wonder.gps,
                    title = "Map",
                    parentScrollEnableState = mutableStateOf(true),
                )
                Spacer(Modifier.height(200.dp))
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
        modifier = modifier.padding(horizontal = 12.dp),
        style = TextStyle(fontSize = 18.sp, lineHeight = 28.sp, lineBreak = LineBreak.Paragraph)
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InfoTitle(
    infoSection: State<InfoSection>
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.Blue),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            Modifier
                .background(
                    Color.Gray,
                    shape = CutCornerShape(corner = CornerSize(5.dp)),
                ).size(120.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            AnimatedContent(
                targetState = infoSection.value,
                transitionSpec = {
                    slideIn { IntOffset(0, it.height * 2) } with
                            slideOut { IntOffset(0, it.height * 2) }
                },
            ) {
                Text(it.title, textAlign = TextAlign.Center)
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().background(Color.Green),
        )
    }
}


@Composable
fun AnimatedDivider() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 72.dp)
            .height(12.dp)
            .background(Color.Cyan)
    )
}

enum class InfoSection(val title: String) {
    FactsAndHistory("Facts & History"),
    Construction("Construction"),
    Location("Location")
}