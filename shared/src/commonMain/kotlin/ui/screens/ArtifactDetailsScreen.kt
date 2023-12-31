package ui.screens

import CompassDivider
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import data.MetRepository
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import models.ArtifactData
import ui.AppIcons
import ui.composables.AppIconButton
import ui.theme.TenorSans
import ui.theme.accent1
import ui.theme.accent2
import ui.theme.black
import ui.theme.greyStrong
import ui.theme.offWhite
import ui.theme.white
import utils.prependProxy

val imageMaxHeight = 400.dp
val imageMinHeight = 250.dp

@OptIn(ExperimentalMaterial3Api::class)
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
    val scrollState = rememberScrollState()

    val density = LocalDensity.current

    val imageHeight by remember {
        derivedStateOf {
            val scrollInDp = with(density) { scrollState.value.toDp() }
            (imageMaxHeight - scrollInDp).coerceAtLeast(imageMinHeight)
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
            .background(greyStrong)
            .verticalScroll(scrollState),
    ) {
        if (result == null) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else if (result!!.isFailure || (result!!.getOrNull() == null)) {
            ArtifactNotFoundError()
        } else {
            val data = result!!.getOrThrow()!!
            Column(
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
                )
            }
        }
        TopAppBar(
            modifier = Modifier.offset {
                IntOffset(0, scrollState.value)
            },
            title = {},
            navigationIcon = {
                AppIconButton(
                    iconPath = AppIcons.Prev,
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
        KamelImage(
            resource = asyncPainterResource(imageUrl.prependProxy()),
            onLoading = {
                CircularProgressIndicator()
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
) {

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = Modifier.background(greyStrong).padding(horizontal = 24.dp),
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
        isVisible = true;
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
