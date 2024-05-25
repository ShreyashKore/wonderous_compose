package ui.screens

//import io.kamel.image.KamelImage
//import io.kamel.image.asyncPainterResource
import CompassDivider
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import data.MetRepository
import kotlinx.coroutines.delay
import models.ArtifactData
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.composables.AppIconButton
import ui.theme.TenorSans
import ui.theme.accent1
import ui.theme.accent2
import ui.theme.black
import ui.theme.greyStrong
import ui.theme.offWhite
import ui.theme.white
import utils.prependProxy
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_prev

val imageMaxHeight = 400.dp
val imageMinHeight = 250.dp

/**
 * This screen demonstrates data fetching in Composable function itself.
 * This should ideally be done in the ViewModel.
 * But since this screen is relatively simple; data fetching inside compose is demonstrated.
 *
 * Data fetching logic is extracted into this separate [ArtifactDetailsScreen].
 */
@Composable
fun ArtifactDetailsScreen(
    artifactId: String,
    onClickBack: () -> Unit,
) {
    // Not an ideal way to handle data fetching. Use ViewModel instead.
    val repo = remember { MetRepository() }
    var result by remember { mutableStateOf<Result<ArtifactData?>?>(null) }
    LaunchedEffect(artifactId) {
        result = repo.getArtifactById(artifactId)
    }
    ArtifactDetailsScreen(
        result = result,
        onClickBack = onClickBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtifactDetailsScreen(
    result: Result<ArtifactData?>?,
    onClickBack: () -> Unit,
) = BoxWithConstraints {

    val scrollState = rememberScrollState()

    val density = LocalDensity.current
    val orientation = orientation

    @Composable
    fun SingleColumnLayout() {
        val data = result!!.getOrThrow()!!
        val imageHeight by remember {
            derivedStateOf {
                val scrollInDp = with(density) { scrollState.value.toDp() }
                (imageMaxHeight - scrollInDp).coerceAtLeast(imageMinHeight)
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ArtifactImage(
                imageUrl = data.image ?: "",
                onImagePressed = {},
                modifier = Modifier.zIndex(1f)
                    .offset {
                        IntOffset(0, scrollState.value)
                    }.fillMaxWidth()
                    .height(imageHeight)
            )
            InfoColumn(
                data = data,
                modifier = Modifier
                    .padding(horizontal = 24.dp),
            )
        }
    }

    @Composable
    fun TwoColumnLayout() {
        val data = result!!.getOrThrow()!!

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            ArtifactImage(
                imageUrl = data.image ?: "",
                onImagePressed = {},
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
            )
            InfoColumn(
                data = data,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 32.dp)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(greyStrong)
            .safeDrawingPadding(),
    ) {
        if (result == null) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else if (result.isFailure || (result.getOrNull() == null)) {
            ArtifactNotFoundError()
        } else {
            if (orientation == Orientation.Vertical)
                SingleColumnLayout()
            else
                TwoColumnLayout()
        }
        TopAppBar(
            title = {},
            navigationIcon = {
                AppIconButton(
                    icon = Res.drawable.icon_prev,
                    contentDescription = "Back",
                    onClick = onClickBack
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = white
            ),
        )
    }

}

@Composable
private fun ArtifactNotFoundError() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Artifact not found.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 240.dp)
        )
    }
}


@Composable
private fun ArtifactImage(
    imageUrl: String,
    onImagePressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.shadow(20.dp, spotColor = black)
            .background(black)
            .padding(bottom = 12.dp),
    ) {
        AsyncImage(
            imageUrl.prependProxy(),
            onLoading = {
                // CircularProgressIndicator()
            },
            contentDescription = null,
            modifier = Modifier.fillMaxSize().clickable { onImagePressed(imageUrl) },
            contentScale = ContentScale.Fit,
        )
    }
}


@Composable
fun InfoColumn(
    data: ArtifactData,
    modifier: Modifier = Modifier,
) {

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = Modifier.background(greyStrong) then modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(800))
        ) {
            Column {
                if (data.culture?.isNotEmpty() == true) {
                    Text(
                        text = data.culture.uppercase(),
                        color = accent1,
                        fontSize = 14.sp,
                        fontFamily = TenorSans,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = offWhite,
                        fontFamily = TenorSans
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        CompassDivider(isExpanded = isVisible)

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DataInfoRow(label = "Date", value = data.date, animIndex = 1)
            DataInfoRow(label = "Period", value = data.period, animIndex = 2)
            DataInfoRow(label = "Geography", value = data.country, animIndex = 3)
            DataInfoRow(label = "Medium", value = data.medium, animIndex = 4)
            DataInfoRow(label = "Dimension", value = data.dimension, animIndex = 5)
            DataInfoRow(label = "Classification", value = data.classification, animIndex = 6)
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(isVisible, enter = fadeIn(tween(1000))) {
            Text(
                text = "The Metropolitan Museum of Art, New York",
                style = MaterialTheme.typography.labelLarge.copy(color = accent2),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(128.dp))
    }
}

@Composable
fun DataInfoRow(label: String, value: String?, animIndex: Long = 1) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(animIndex * 100)
        isVisible = true
    }
    AnimatedVisibility(
        isVisible,
        enter = slideInHorizontally(tween(1000)) { it / 2 } + fadeIn(tween(800))
    ) {
        Row(
            modifier = Modifier.padding(bottom = 24.dp),
        ) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(color = accent2, fontSize = 16.sp),
                modifier = Modifier.weight(0.4f)
            )
            Text(
                text = value?.ifBlank { "--" } ?: "--",
                style = MaterialTheme.typography.bodyLarge.copy(color = offWhite),
                modifier = Modifier.weight(0.6f)
            )
        }
    }
}

@Preview
@Composable
private fun ArtifactDetailsScreenPreview() {
    ArtifactDetailsScreen(
        result = Result.success(
            ArtifactData(
                12,
                "Title",
                "https://www.metmuseum.org/art/collection/search/1",
            )
        ),
        onClickBack = { }
    )
}