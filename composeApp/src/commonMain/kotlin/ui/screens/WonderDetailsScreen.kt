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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import models.Wonder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.screens.WonderDetailsScreen.ArtifactCarousel
import ui.screens.WonderDetailsScreen.Editorial
import ui.screens.WonderDetailsScreen.PhotoGallery
import ui.screens.WonderDetailsScreen.WonderEvents
import ui.theme.black
import ui.theme.fgColor
import ui.theme.white
import ui.utils.filePainterResource
import ui.wonderButtonPath
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.tab_artifacts
import wonderouscompose.composeapp.generated.resources.tab_artifacts_active
import wonderouscompose.composeapp.generated.resources.tab_editorial
import wonderouscompose.composeapp.generated.resources.tab_editorial_active
import wonderouscompose.composeapp.generated.resources.tab_photos
import wonderouscompose.composeapp.generated.resources.tab_photos_active
import wonderouscompose.composeapp.generated.resources.tab_timeline
import wonderouscompose.composeapp.generated.resources.tab_timeline_active


@Composable
fun WonderDetailsScreen(
    onPressHome: () -> Unit,
    navigateToTimeline: () -> Unit,
    openArtifactDetailsScreen: (id: String) -> Unit,
    openArtifactsScreen: () -> Unit,
    openMapScreen: (Wonder) -> Unit,
    openVideoScreen: (videoId: String) -> Unit,
    wonder: Wonder,
) = BoxWithConstraints {
    var currentScreen by rememberSaveable { mutableStateOf(Editorial) }
    val navbarMode = if (maxWidth > 800.dp) NavBarMode.NavRail else NavBarMode.BottomBar

    Scaffold(
        bottomBar = {
            if (navbarMode == NavBarMode.BottomBar)
                NavigationBar(
                    wonder = wonder,
                    onPressHome = onPressHome,
                    currentScreen = currentScreen,
                    onClickDestination = { currentScreen = it },
                    mode = navbarMode
                )
        }
    ) { padding ->
        AnimatedContent(
            currentScreen,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            modifier = Modifier.run {
                if (navbarMode == NavBarMode.NavRail) padding(
                    start = if (navbarMode == NavBarMode.NavRail && currentScreen != PhotoGallery)
                        navRailWidth else 0.dp
                ) else this

            }
        ) { currentSelected ->
            when (currentSelected) {
                Editorial -> EditorialScreen(
                    wonder = wonder,
                    openHomeScreen = onPressHome,
                    openMapScreen = openMapScreen,
                    openVideoScreen = openVideoScreen,
                )

                PhotoGallery -> PhotoGallery(wonder = wonder)
                ArtifactCarousel -> ArtifactCarouselScreen(
                    wonder = wonder,
                    openArtifactDetailsScreen = openArtifactDetailsScreen,
                    openAllArtifactsScreen = openArtifactsScreen,
                )

                WonderEvents -> WonderEvents(wonder = wonder, navigateToTimeline)
            }
        }

        if (navbarMode == NavBarMode.NavRail)
            NavigationBar(
                wonder = wonder,
                onPressHome = onPressHome,
                currentScreen = currentScreen,
                onClickDestination = { currentScreen = it },
                mode = navbarMode
            )

    }
}


@Composable
private fun NavigationBar(
    wonder: Wonder,
    currentScreen: WonderDetailsScreen,
    onClickDestination: (WonderDetailsScreen) -> Unit,
    onPressHome: () -> Unit,
    mode: NavBarMode,
) {
    val isBgTransparent = currentScreen == PhotoGallery
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

    val content = @Composable {
        WonderButton(
            wonder = wonder,
            borderWidth = wonderBtnBorderWidth,
            onClick = onPressHome,
            modifier = Modifier
                .requiredSize(76.dp)
                .offset(
                    x = if (mode == NavBarMode.NavRail) 8.dp else 0.dp,
                    y = if (mode == NavBarMode.BottomBar) (-10).dp else 0.dp
                )
        )

        WonderDetailsScreen.entries.map { destination ->
            NavDestinationButton(
                icon = destination.icon,
                activeIcon = destination.activeIcon,
                contentDescription = destination.title,
                isSelected = currentScreen == destination,
                unSelectedColor = contentColor,
                onClick = { onClickDestination(destination) },
                modifier = Modifier.padding(4.dp)
            )
        }
    }

    when (mode) {
        NavBarMode.NavRail -> Column(
            Modifier.fillMaxHeight()
                .width(navRailWidth)
                .zIndex(1f)
                .background(bgColor).padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {// Wonder Button
            content()
        }
        // bottom bar
        NavBarMode.BottomBar -> Row(
            Modifier
                .fillMaxWidth()
                .background(bgColor)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(bottomBarHeight)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            content()
        }
    }

}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun WonderButton(
    wonder: Wonder,
    borderWidth: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        filePainterResource(wonder.wonderButtonPath),
        contentDescription = "home",
        modifier = modifier
            .background(white, CircleShape) // padding as border
            .padding(borderWidth) // Using padding as border to influence size change when border changes
            .clip(CircleShape)
            .background(wonder.fgColor)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun NavDestinationButton(
    icon: DrawableResource,
    activeIcon: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    unSelectedColor: Color,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val curIcon = if (isSelected) activeIcon else icon
    val iconTint = if (isSelected) MaterialTheme.colorScheme.primary else unSelectedColor
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painterResource(curIcon),
            contentDescription = contentDescription,
            modifier = Modifier.size(26.dp),
            tint = iconTint
        )
    }
}


private val bottomBarHeight = 72.dp
private val navRailWidth = 72.dp

private enum class NavBarMode {
    BottomBar, NavRail
}

private enum class WonderDetailsScreen(
    val title: String,
    val icon: DrawableResource,
    val activeIcon: DrawableResource,
) {
    Editorial(
        "Editorial",
        Res.drawable.tab_editorial,
        Res.drawable.tab_editorial_active,
    ),
    PhotoGallery(
        "Photo Gallery",
        Res.drawable.tab_photos,
        Res.drawable.tab_photos_active,
    ),
    ArtifactCarousel(
        "Artifact Carousel",
        Res.drawable.tab_artifacts,
        Res.drawable.tab_artifacts_active,
    ),
    WonderEvents(
        "Wonder Events",
        Res.drawable.tab_timeline,
        Res.drawable.tab_timeline_active,
    ),
}