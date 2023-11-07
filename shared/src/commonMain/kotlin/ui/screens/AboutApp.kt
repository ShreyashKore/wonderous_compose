package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.Raleway
import ui.theme.accent1
import ui.theme.black
import ui.theme.offWhite

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AboutApp(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(offWhite, RoundedCornerShape(8.dp))
            .padding(20.dp)
    ) {
        Column {
            Row {
                Image(
                    painterResource("images/common/app-logo-plain.png"),
                    contentDescription = "logo",
                    modifier = Modifier.background(black).padding(4.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Wonderous Compose", fontSize = 24.sp, fontFamily = Raleway)
                    Text("2.0.14")
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                buildAnnotatedString {
                    append("Wonderous is a visual showcase of eight wonders of the world. Built with ")
                    appendLink("Flutter")
                    append(" by the team of ")
                    appendLink("gskinner.")
                    append("\n\n")
                    append("Learn more at ")
                    appendLink("wonderouse.app.")
                    append("To see the source code for this app, please visit the ")
                    appendLink("Wonderous github repo.")
                    append("As explained in our Privacy Policy, we do not collect any personal information.")
                    append("\n\n")
                    append("Public-domain artwork from ")
                    appendLink("The Metropolitan Museum of Art, New Your.")
                    append("Photography from ")
                    appendLink("Unsplash.")
                },
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = {}) {
                    Text("VIEW LICENSES")
                }
                TextButton(onClick = onDismissRequest) {
                    Text("CLOSE")
                }
            }
        }
    }
}


@Composable
fun AnnotatedString.Builder.appendLink(str: String) = withStyle(linkStyle) {
    append(str)
}

val linkStyle
    @Composable get() = SpanStyle(
        color = accent1
    )