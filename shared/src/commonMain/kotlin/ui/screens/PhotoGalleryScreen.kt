package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.UnsplashPhotoData
import data.UnsplashPhotoSize
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.Wonder
import ui.composables.SimpleGrid
import ui.theme.black
import ui.utils.eightWaySwipeDetector
import ui.utils.roundToIntOffset
import ui.utils.simpleTransformable
import kotlin.math.roundToInt

// TODO: move into separate file
val BackgroundColor = Color.Black

@OptIn(ExperimentalStdlibApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PhotoGallery(
    wonder: Wonder,
) = BoxWithConstraints(Modifier.background(BackgroundColor)) {
    val gridSize = 5

    val imgCount = gridSize * gridSize

    var currentIndex by remember { mutableStateOf((gridSize * gridSize / 2f).roundToInt()) }

    val density = LocalDensity.current

    var photoIds by remember { mutableStateOf(listOf<String>()) }

    val collectionId = wonder.unsplashCollectionId

    LaunchedEffect(collectionId) {
        val ids = UnsplashPhotoData.photosByCollectionId[collectionId]?.toMutableList()
        if (!ids.isNullOrEmpty()) {
            // Ensure we have enough images to fill the grid, repeat if necessary
            while (ids.size < imgCount) {
                ids.addAll(ids)
                if (ids.size > imgCount) ids.subList(imgCount, ids.size).clear()
            }
        }
        photoIds = ids?.toList() ?: emptyList()
    }


    val totalSize = DpSize(maxWidth * 0.7f, maxHeight * 0.5f)
    val totalSizePx = with(density) { totalSize.toSize() }
    val padding = 10.dp

    val originOffset = with(density) {
        val screenCenter = Offset(maxWidth.toPx() / 2f, maxHeight.toPx() / 2f)
        val imageCenter = Offset(totalSizePx.width / 2f, totalSizePx.height / 2f)
        screenCenter - imageCenter
    }
    val col = currentIndex % gridSize
    val row = currentIndex / gridSize
    val indexedOffset by animateOffsetAsState(
        Offset(-totalSizePx.width * col, -totalSizePx.height * row)
    )
    val gridOffset = originOffset + indexedOffset

    val urls = remember(photoIds) {
        photoIds.map {
            UnsplashPhotoData.getSelfHostedUrl(it, UnsplashPhotoSize.MED)
        }
    }

    var showFullScreenImage by remember { mutableStateOf(false) }

    fun onTap(i: Int) {
        if (i == currentIndex) {
            showFullScreenImage = true
        } else {
            currentIndex = i
        }
    }

    fun onSwipe(direction: Offset) {
        val (x, y) = direction
        // Calculate next index using magic
        val newIndex = currentIndex +
                (if (y == 0f) 0 else if (y > 0) -5 else 5) +
                (if (x == 0f) 0 else if (x > 0) -1 else 1)

        // Check if the index is valid; This discards trying to cross top/bottom boundaries
        if (newIndex in 0..<imgCount) {
            // for valid a index, first check if we are crossing left/right boundaries
            val swipedLeftWhenAtLeftBoundary = x < 0 && newIndex % gridSize == 0
            val swipedRightWhenAtRightBoundary = x > 0 && newIndex % gridSize == gridSize - 1
            if (swipedLeftWhenAtLeftBoundary || swipedRightWhenAtRightBoundary) {
                return
            }
            currentIndex = newIndex
        }
    }

    SimpleGrid(
        modifier = Modifier
            .eightWaySwipeDetector(onSwipe = ::onSwipe)
            .offset { gridOffset.roundToIntOffset() },
        columnCount = gridSize,
    ) {
        urls.forEachIndexed { i, photoId ->
            UnsplashImage(
                modifier = Modifier.size(totalSize).padding(padding),
                onTap = { onTap(i) },
                isSelected = i == currentIndex,
                photoUrl = photoId,
            )
        }
    }

    AnimatedCutOut(
        modifier = Modifier.fillMaxSize(),
        currentIndex = currentIndex,
        cutoutSize = totalSize - DpSize(padding, padding),
    )


    AnimatedVisibility(
        showFullScreenImage, enter = fadeIn(), exit = fadeOut()
    ) {
        FullscreenUrlImgViewer(
            url = urls[currentIndex],
            onDismiss = { showFullScreenImage = false },
        )
    }

}

@Composable
private fun AnimatedCutOut(
    modifier: Modifier = Modifier,
    currentIndex: Int,
    cutoutSize: DpSize,
    cornerRadius: Dp = 12.dp
) {
    val animatable = remember(currentIndex) { Animatable(0.8f) }
    LaunchedEffect(currentIndex) {
        animatable.animateTo(1f, animationSpec = tween(durationMillis = 500))
    }
    val cutOutHeight = cutoutSize.height * animatable.value

    Box(
        modifier.cutOut(cutoutSize.copy(height = cutOutHeight), cornerRadius)
    ) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.radialGradient(
                    0f to BackgroundColor.copy(alpha = 0.4f),
                    1f to BackgroundColor.copy(alpha = 0.8f),
                )
            )
        )
    }
}


@Composable
private fun UnsplashImage(
    modifier: Modifier = Modifier,
    onTap: () -> Unit,
    isSelected: Boolean,
    photoUrl: String,
) {
    val animSpec = tween<Float>(durationMillis = 800)
    val imageScale by animateFloatAsState(if (isSelected) 1.1f else 1f, animSpec)
    val painter = asyncPainterResource(photoUrl)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .scale(imageScale)
            .clickable(onClick = onTap),
        contentAlignment = Alignment.Center,
    ) {
        KamelImage(
            resource = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onLoading = {
                CircularProgressIndicator()
            }
        )
    }
}


@Composable
fun FullscreenUrlImgViewer(url: String, onDismiss: () -> Unit) {
    Box(
        Modifier.fillMaxSize().background(black.copy(alpha = .6f)).clickable { onDismiss() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            rememberImagePainter(url), contentDescription = null,
            modifier = Modifier.simpleTransformable().fillMaxSize().padding(24.dp),
        )
    }
}


fun Modifier.cutOut(
    cutoutSize: DpSize,
    cornerRadius: Dp
) = drawWithContent {
    val padX = (size.width - cutoutSize.width.toPx()) / 2
    val padY = (size.height - cutoutSize.height.toPx()) / 2
    clipPath(
        Path.combine(
            PathOperation.Difference,
            Path().apply {
                addRect(Rect(0f, 0f, size.width, size.height))
            },
            Path().apply {
                addRoundRect(
                    RoundRect(
                        padX,
                        padY,
                        size.width - padX,
                        size.height - padY,
                        CornerRadius(cornerRadius.toPx())
                    )
                )
            }
        )
    ) { this@drawWithContent.drawContent() }

}

