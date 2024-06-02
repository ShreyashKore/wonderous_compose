package ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_next
import wonderouscompose.composeapp.generated.resources.icon_prev

@Composable
fun PreviousNextNavigation(
    onPreviousPressed: (() -> Unit)? = null,
    onNextPressed: (() -> Unit)? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    if (!enabled) {
        content()
        return
    }

    Box(
        modifier = modifier
            .onKeyEvent {
                when (it.key) {
                    Key.DirectionLeft -> {
                        onPreviousPressed?.invoke()
                        true
                    }

                    Key.DirectionRight -> {
                        onNextPressed?.invoke()
                        true
                    }

                    else -> false

                }
            }
    ) {
        content()
        Row(
            modifier = Modifier
                .widthIn(max = 840.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            AppIconButton(
                Res.drawable.icon_prev,
                contentDescription = "Previous",
                onClick = { onPreviousPressed?.invoke() },
                enabled = onPreviousPressed != null,
            )
            Spacer(Modifier.weight(1f))
            AppIconButton(
                Res.drawable.icon_next,
                contentDescription = "Next",
                onClick = { onNextPressed?.invoke() },
                enabled = onNextPressed != null,
            )
        }
    }
}