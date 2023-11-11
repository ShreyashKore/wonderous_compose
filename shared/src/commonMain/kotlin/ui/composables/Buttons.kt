package ui.composables

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.black
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
                .background(black)
                .padding(8.dp)
                .size(32.dp),
            tint = white
        )
    }
}


@Composable
fun LongButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = greyStrong,
            contentColor = white
        ),
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(label)
    }
}