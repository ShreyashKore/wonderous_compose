package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ImagePaths
import ui.getAssetPath
import ui.screens.WonderDetailsDest.*
import ui.theme.black
import ui.theme.fgColor
import ui.theme.white


@Composable
fun WonderDetailsScreen(
    onPressHome: () -> Unit,
    wonder: Wonder,
) {
    var currentSelected by remember { mutableStateOf(Editorial) }

    val navigateToTimeline = remember {
        { }
    }
    Scaffold(
        bottomBar = {
            BottomBar(
                wonder = wonder,
                selected = currentSelected,
                onSelected = { currentSelected = it },
                onPressHome = onPressHome
            )
        }
    ) {
        AnimatedContent(
            currentSelected,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
        ) { currentSelected ->
            when (currentSelected) {
                Editorial -> EditorialScreen(wonder = wonder, onPressHome)
                PhotoGallery -> PhotoGallery(wonder = wonder)
                ArtifactCarousel -> ArtifactCarouselScreen(wonder = wonder)
                WonderEvents -> WonderEvents(wonder = wonder, navigateToTimeline)
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class, ExperimentalStdlibApi::class)
@Composable
private fun BottomBar(
    wonder: Wonder,
    selected: WonderDetailsDest,
    onSelected: (WonderDetailsDest) -> Unit,
    onPressHome: () -> Unit,
) {
    val isBgTransparent = selected == PhotoGallery
    val bgColor by animateColorAsState(
        targetValue = if (isBgTransparent) Color.Transparent else white,
        animationSpec = tween(500)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isBgTransparent) Color.White else black,
        animationSpec = tween(500)
    )
    val wonderBtnBorderWidth by animateDpAsState(
        targetValue = if (isBgTransparent) 2.dp else 6.dp,
        animationSpec = tween(800)
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(82.dp)
    ) {

        Box(
            Modifier.padding(top = 12.dp).fillMaxSize()
                .background(bgColor)
        )
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painterResource(wonder.getAssetPath("wonder-button.png")),
                contentDescription = "home",
                modifier = Modifier
                    .size(76.dp)
                    .background(white, CircleShape)
                    .padding(wonderBtnBorderWidth)
                    .clip(CircleShape)
                    .background(wonder.fgColor)
                    .clickable {
                        onPressHome()
                    }

            )
            Row(
                Modifier.weight(1f).height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                entries.map { destination ->
                    AppBarIcon(
                        destination.icon,
                        selected = selected == destination,
                        unSelectedColor = contentColor,
                        onClick = { onSelected(destination) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AppBarIcon(
    icon: String,
    selected: Boolean = false,
    unSelectedColor: Color,
    onClick: () -> Unit
) {
    val iconImgPath = "${ImagePaths.common}/3.0x/tab-${icon}${if (selected) "-active" else ""}.png"
    val iconTint = if (selected) MaterialTheme.colorScheme.primary else unSelectedColor
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painterResource(iconImgPath),
            contentDescription = icon,
            modifier = Modifier.size(26.dp),
            tint = iconTint
        )
    }
}

private enum class WonderDetailsDest(val title: String, val icon: String) {
    Editorial("Editorial", "editorial"),
    PhotoGallery("Photo Gallery", "photos"),
    ArtifactCarousel("Artifact Carousel", "artifacts"),
    WonderEvents("Wonder Events", "timeline")
}