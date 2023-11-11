import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CompassDivider(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    duration: Duration = 3000.milliseconds,
    linesColor: Color? = null,
) {
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = TweenSpec(durationMillis = duration.inWholeMilliseconds.toInt()),
    )

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 0.5f else 0f,
        animationSpec = TweenSpec(durationMillis = duration.inWholeMilliseconds.toInt())
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Divider(
                color = linesColor ?: MaterialTheme.colorScheme.primary,
                thickness = Dp.Hairline,
                modifier = Modifier.fillMaxWidth(scale).align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Image(
            modifier = Modifier.size(42.dp)
                .graphicsLayer(
                    rotationZ = rotationAngle * 360f
                ),
            painter = painterResource("images/common/compass_full.xml"),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(8.dp))
        Box(modifier = Modifier.weight(1f)) {
            Divider(
                color = linesColor ?: MaterialTheme.colorScheme.primary,
                thickness = Dp.Hairline,
                modifier = Modifier.fillMaxWidth(scale).align(Alignment.CenterStart).graphicsLayer(
                    rotationX = if (isExpanded) 0f else 1f
                )
            )
        }
    }
}