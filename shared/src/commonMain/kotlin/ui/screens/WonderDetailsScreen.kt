package ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ui.theme.bgColor


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun WonderDetailsScreen(
    onPressHome: () -> Unit,
    wonder: Wonder,
) {
    var currentSelected by remember { mutableStateOf(0) }

    val navigateToTimeline = remember {
        { }
    }
    Scaffold(
        bottomBar = {
            AppBar(
                wonder = wonder,
                selected = currentSelected,
                onSelected = { currentSelected = it },
                onPressHome = onPressHome
            )
        }
    ) {
        when (currentSelected) {
            0 -> EditorialScreen(wonder = wonder, onPressHome)
            1 -> PhotoGallery(wonder = wonder)
            2 -> ArtifactCarouselScreen(wonder = wonder)
            3 -> WonderEvents(wonder = wonder, navigateToTimeline)
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AppBar(
    wonder: Wonder,
    selected: Int,
    onSelected: (Int) -> Unit,
    onPressHome: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        Box(Modifier.padding(top = 12.dp).fillMaxSize().background(Color.White))
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painterResource(wonder.getAssetPath("wonder-button.png")),
                contentDescription = "home",
                modifier = Modifier.size(80.dp)
                    .border(BorderStroke(width = 6.dp, Color.White), CircleShape)
                    .clip(CircleShape)
                    .background(wonder.bgColor)
                    .clickable {
                        onPressHome()
                    }
            )
            Row(
                Modifier.fillMaxWidth().height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                AppBarIcon("editorial", selected = selected == 0, onClick = { onSelected(0) })
                AppBarIcon("photos", selected = selected == 1, onClick = { onSelected(1) })
                AppBarIcon("artifacts", selected = selected == 2, onClick = { onSelected(2) })
                AppBarIcon("timeline", selected = selected == 3, onClick = { onSelected(3) })
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AppBarIcon(
    icon: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val iconImgPath = "${ImagePaths.common}/tab-${icon}${if (selected) "-active" else ""}.png"
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painterResource(iconImgPath),
            contentDescription = icon,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}