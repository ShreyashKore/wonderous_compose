package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_prev

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconButton(
        icon = Res.drawable.icon_prev,
        contentDescription = "Back",
        onClick = onClick,
        modifier = modifier,
    )
}