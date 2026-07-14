package ui.composables

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_next
import wonderouscompose.composeapp.generated.resources.icon_prev
import wonderouscompose.composeapp.generated.resources.semanticsNext
import wonderouscompose.composeapp.generated.resources.semanticsPrevious

@Composable
fun PreviousNextNavigation(
    onPreviousPressed: (() -> Unit)? = null,
    onNextPressed: (() -> Unit)? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val rememberContent = remember(content) {
        movableContentOf(content)
    }

    if (!enabled) {
        rememberContent()
        return
    }
    val navFocusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        delay(300)
        navFocusRequester.requestFocus()
    }
    Box(
        modifier = modifier
            .focusRequester(navFocusRequester)
            .onKeyEvent { keyEvent ->
                if (keyEvent.type != KeyEventType.KeyDown) return@onKeyEvent false
                when (keyEvent.key) {
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
            .focusable()
    ) {
        rememberContent()
        Row(
            modifier = Modifier
                .widthIn(max = 840.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            AppIconButton(
                Res.drawable.icon_prev,
                contentDescription = stringResource(Res.string.semanticsPrevious),
                onClick = { onPreviousPressed?.invoke() },
                enabled = onPreviousPressed != null,
            )
            Spacer(Modifier.weight(1f))
            AppIconButton(
                Res.drawable.icon_next,
                contentDescription = stringResource(Res.string.semanticsNext),
                onClick = { onNextPressed?.invoke() },
                enabled = onNextPressed != null,
            )
        }
    }
}