package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import data.HighlightData
import kotlinx.coroutines.launch
import models.ChichenItza
import models.Wonder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composables.AppIconButton
import ui.composables.LongButton
import ui.composables.PreviousNextNavigation
import ui.theme.TenorSans
import ui.theme.greyMedium
import ui.theme.greyStrong
import ui.theme.offWhite
import ui.theme.white
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.artifactsButtonBrowse
import wonderouscompose.composeapp.generated.resources.artifactsTitleArtifacts
import wonderouscompose.composeapp.generated.resources.icon_search
import kotlin.math.absoluteValue


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun ArtifactCarouselScreen(
    wonder: Wonder,
    openArtifactDetailsScreen: (id: String) -> Unit,
    openAllArtifactsScreen: () -> Unit,
) = BoxWithConstraints {
    val maxWidth = maxWidth
    val maxHeight = maxHeight

    val minDimension = minOf(maxHeight, maxWidth)

    val artifacts = remember(wonder) { HighlightData.forWonder(wonder) }

    val pagerState = rememberPagerState(
        initialPage = 10000 / 2,
        initialPageOffsetFraction = 0f,
        pageCount = { 10000 }
    )
    val coroutineScope = rememberCoroutineScope()

    val currentArtifact = artifacts[pagerState.currentPage % artifacts.size]
    // background
    Box(Modifier.fillMaxSize()) {
        // background blurred image of Artifact
        AnimatedContent(
            currentArtifact.imageUrl,
            transitionSpec = {
                fadeIn(tween(durationMillis = 600)) togetherWith
                        fadeOut(tween(durationMillis = 800))
            },
        ) { imageUrl ->
            AsyncImage(
                imageUrl,
                contentDescription = "background",
                modifier = Modifier.blur(8.dp)
                    .background(greyStrong).fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.cropScaled(1.4f),
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), BlendMode.Multiply),
            )
        }
        /// Background bottom circle
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .requiredSize(minDimension * 2)
                .offset(y = minDimension)
                .clip(CircleShape)
                .background(greyMedium.copy(.4f).compositeOver(offWhite))
        )
    }

    // foreground content
    Column(
        modifier = Modifier.safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(Res.string.artifactsTitleArtifacts),
                    style = MaterialTheme.typography.bodyLarge,
                    color = white
                )
            },
            actions = {
                AppIconButton(
                    icon = Res.drawable.icon_search,
                    contentDescription = stringResource(Res.string.artifactsButtonBrowse),
                    onClick = openAllArtifactsScreen,
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Transparent)
        )


        Box(
            Modifier.weight(1f),
        ) {
            // Artifact Image
            PreviousNextNavigation(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight(),
                onPreviousPressed = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onNextPressed = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                enabled = maxWidth > 600.dp,
            ) {
                HorizontalPager(
                    modifier = Modifier.fillMaxHeight(),
                    state = pagerState,
                    contentPadding = PaddingValues(
                        horizontal = (maxWidth - 200.dp) / 2
                    ),
                    pageSize = PageSize.Fixed(200.dp),
                    pageSpacing = 20.dp,
                    verticalAlignment = Alignment.Top,
                ) { pageNo ->
                    val index = pageNo % artifacts.size
                    val artifact = artifacts[index]
                    ArtifactImageInBox(
                        name = artifact.title,
                        url = artifact.imageUrl,
                        isSelected = pagerState.currentPage == pageNo,
                        onClick = {
                            // When clicked on current artifact; open it's details else bring the clicked artifact to center
                            if (artifact.id == currentArtifact.id)
                                openArtifactDetailsScreen(artifact.id)
                            else coroutineScope.launch {
                                pagerState.animateScrollToPage(pageNo)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .fillMaxHeight(.6f)
                            .graphicsLayer {
                                val pageOffset =
                                    ((pagerState.currentPage - pageNo) + pagerState.currentPageOffsetFraction).absoluteValue
                                translationY = lerp(0.dp, 60.dp, fraction = pageOffset).toPx()
                            }
                    )
                }
            }

            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Artifact title
                AnimatedContent(
                    modifier = Modifier.widthIn(max = 600.dp),
                    targetState = currentArtifact.title,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    contentAlignment = Alignment.Center,
                ) { title ->
                    Text(
                        title,
                        fontFamily = TenorSans,
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(Modifier.height(24.dp))

                LongButton(
                    label = stringResource(Res.string.artifactsButtonBrowse),
                    onClick = openAllArtifactsScreen,
                    modifier = Modifier.widthIn(max = 400.dp)
                )
            }
        }
    }
}

@Composable
private fun ArtifactImageInBox(
    name: String,
    url: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {

    val height by animateFloatAsState(if (isSelected) 1f else .4f, animationSpec = tween(800))
    val width by animateFloatAsState(if (isSelected) 1f else .8f, animationSpec = tween(800))

    Box(
        modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            url,
            contentDescription = name,
            modifier = Modifier
                .border(1.dp, SolidColor(white), RoundedCornerShape(percent = 100))
                .padding(12.dp).clip(RoundedCornerShape(percent = 100)).clickable(onClick = onClick)
                .fillMaxHeight(height)
                .fillMaxWidth(width),
            contentScale = ContentScale.Crop,
            onLoading = {
                // Box(
                //     Modifier.fillMaxSize().background(greyMedium),
                //     contentAlignment = Alignment.Center
                // ) {
                //     CircularProgressIndicator()
                // }
            },
            onError = {
                // Image(
                //     painterResource(ImagePaths.noImagePlaceholder),
                //     contentDescription = null,
                //     contentScale = ContentScale.Crop
                // )
            }
        )
    }

}

fun ContentScale.Companion.cropScaled(scale: Float = 1f) = object : ContentScale {
    override fun computeScaleFactor(srcSize: Size, dstSize: Size): ScaleFactor {
        return Crop.computeScaleFactor(srcSize, dstSize) * scale
    }
}


@Preview
@Composable
private fun Preview() {
    ArtifactCarouselScreen(ChichenItza, {}, {})
}