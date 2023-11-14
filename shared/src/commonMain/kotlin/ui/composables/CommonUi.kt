package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.AppIcons

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconButton(
        iconPath = AppIcons.Prev,
        contentDescription = "Back",
        onClick = onClick,
        modifier = modifier,
    )
}