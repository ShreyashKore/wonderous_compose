package dev.shreyash.wonderouscompose.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import wonderous_compose.shared.generated.resources.Res
import wonderous_compose.shared.generated.resources.circleButtonsSemanticBack
import wonderous_compose.shared.generated.resources.icon_prev


@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconButton(
        icon = Res.drawable.icon_prev,
        contentDescription = stringResource(Res.string.circleButtonsSemanticBack),
        onClick = onClick,
        modifier = modifier,
    )
}