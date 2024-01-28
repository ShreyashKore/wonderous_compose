package ui.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.AppIcons
import ui.theme.greyStrong
import ui.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppIconButton(
    iconPath: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material.IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painterResource(iconPath),
            contentDescription = contentDescription,
            modifier = Modifier
                .clip(CircleShape)
                .background(greyStrong)
                .padding(8.dp)
                .size(32.dp),
            tint = white
        )
    }
}


@Composable
fun LongButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = greyStrong,
            contentColor = white
        ),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(label)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GithubButton() {
    val uriHandler = LocalUriHandler.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val size by animateDpAsState(if (isHovered) 60.dp else 48.dp)

    Image(
        painterResource(AppIcons.Github),
        contentDescription = "Github Repository",
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .clip(CircleShape)
            .size(size)
            .clickable { uriHandler.openUri("https://github.com/ShreyashKore/wonderous_compose") },
    )
}