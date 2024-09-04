package ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter

@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    // For future use and to minimize changes in current code
    onLoading: @Composable (() -> Unit)? = null,
    onError: @Composable ((Exception) -> Unit)? = null,
) = Image(
    painter = rememberImagePainter(
        url = url,
    ),
    contentDescription = contentDescription,
    contentScale = contentScale,
    modifier = modifier,
    colorFilter = colorFilter
)
