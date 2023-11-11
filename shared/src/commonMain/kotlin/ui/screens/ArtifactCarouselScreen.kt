package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import data.HighlightData
import models.Wonder
import ui.composables.LongButton
import ui.theme.TenorSans
import ui.theme.offWhite
import ui.theme.white
import kotlin.math.absoluteValue

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun ArtifactCarouselScreen(
    wonder: Wonder,
    openArtifactDetailsScreen: (id: String) -> Unit,
    openAllArtifactsScreen: () -> Unit,
) {
    val artifacts = remember(wonder) { HighlightData.forWonder(wonder) }

    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        initialPageOffsetFraction = 0f,
        pageCount = { Int.MAX_VALUE }
    )

    val currentArtifact = artifacts[pagerState.currentPage % artifacts.size]
    // background
    Box(Modifier.fillMaxSize()) {
        AnimatedContent(
            currentArtifact.imageUrl,
            transitionSpec = {
                fadeIn(tween(durationMillis = 600)) togetherWith
                        fadeOut(tween(durationMillis = 800))
            },
        ) { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "background",
                modifier = Modifier.blur(2.dp).fillMaxWidth().fillMaxHeight(0.7f),
                contentScale = FixedScale(1.5f),
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), BlendMode.Multiply)
            )
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .scale(scaleX = 2.2f, scaleY = 1f)
                .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
                .background(offWhite)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)

        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            Box( // Title bar
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "ARTIFACTS",
                    style = MaterialTheme.typography.bodyLarge,
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = openAllArtifactsScreen
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = null)
                }
            }
        }

        HorizontalPager(
            modifier = Modifier.weight(.65f),
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = 100.dp
            ),
            pageSpacing = 20.dp,
            verticalAlignment = Alignment.Bottom,
        ) { pageNo ->
            val index = pageNo % artifacts.size
            val artifact = artifacts[index]
            ArtifactImage(
                name = artifact.title,
                image = artifact.imageUrlSmall,
                isSelected = pagerState.currentPage == pageNo,
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - pageNo) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        println("Page Offset $pageOffset")
                        translationY = lerp(0.dp, 40.dp, fraction = pageOffset).toPx()
                    }.clickable {
                        openArtifactDetailsScreen(artifact.id)
                    }
            )
        }

        Spacer(Modifier.height(20.dp))

        Column(
            Modifier.fillMaxHeight(.35f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = currentArtifact.title,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { title ->
                Text(
                    title,
                    fontFamily = TenorSans,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            LongButton(label = "BROWSE ALL ARTIFACTS", onClick = openAllArtifactsScreen)
        }



        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun ArtifactImage(
    modifier: Modifier = Modifier,
    name: String,
    image: String,
    isSelected: Boolean,
) {
    val imagePainter = rememberImagePainter(image)

    val height by animateDpAsState(if (isSelected) 300.dp else 130.dp, animationSpec = tween(800))
    val width by animateDpAsState(if (isSelected) 200.dp else 130.dp, animationSpec = tween(800))

    Box(
        modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = imagePainter,
            contentDescription = name,
            modifier = Modifier
                .border(1.dp, SolidColor(white), RoundedCornerShape(percent = 100))
                .padding(12.dp).clip(RoundedCornerShape(percent = 100))
                .size(DpSize(width = width, height = height)),
            contentScale = ContentScale.Crop
        )
    }

}
