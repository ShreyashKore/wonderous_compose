package ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.black
import ui.theme.white

@OptIn(ExperimentalResourceApi::class)
@Composable
fun YouTubeThumbnail(
    id: String,
    showPlayButton: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val imageUrl = "https://img.youtube.com/vi/$id/hqdefault.jpg"

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(
                imageUrl,
                errorPainter = { painterResource("compose-multiplatform.xml") }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .background(Color.Gray)
        )
        if (showPlayButton) {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = white,
                    modifier = Modifier
                        .size(100.dp)
                        .background(black.copy(.5f), CircleShape),
                )
            }
        }
    }
}
