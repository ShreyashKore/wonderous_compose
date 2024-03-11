package ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.Raleway
import ui.theme.accent1
import ui.theme.black
import ui.theme.offWhite
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.app_logo_plain

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun AboutApp(onDismissRequest: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(offWhite, RoundedCornerShape(8.dp))
            .padding(20.dp)
    ) {
        Column {
            Row {
                Image(
                    painterResource(Res.drawable.app_logo_plain),
                    contentDescription = "logo",
                    modifier = Modifier.background(black).padding(4.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Wonderous Compose", fontSize = 24.sp, fontFamily = Raleway)
                    Text("1.0.0")
                }
            }
            Spacer(Modifier.height(16.dp))

            val text = buildAnnotatedString {
                append("Wonderous Compose is a port of Wonderous in ")
                appendLink(
                    "Compose Multiplatform. ",
                    url = "https://www.jetbrains.com/lp/compose-multiplatform/"
                )
                append(
                    "Wonderous Compose is a visual showcase of eight wonders of the world.\n\n" +
                            "The original project was built by team "
                )
                appendLink("gskinner", url = "https://gskinner.com/flutter")
                append(" using ")
                appendLink("Flutter.", url = "https://flutter.dev")
                append(" This project is a tribute to ")
                appendLink("original project", url = "https://flutter.gskinner.com/wonderous/")
                append(" with an aim to explore the design possibilities with Compose Multiplatform.")
                append("\n\n")
                append("To see the source code for this app, please visit the ")
                appendLink(
                    "Wonderous Compose github repo\n\n",
                    url = "https://github.com/ShreyashKore/wonderous_compose"
                )

                append("Artworks and logos are taken from original project's ")
                appendLink(
                    "github repo.",
                    url = "https://github.com/gskinnerTeam/flutter-wonderous-app"
                )
                append(" Public-domain artwork from ")
                appendLink(
                    "The Metropolitan Museum of Art, New Your.",
                    url = "https://www.metmuseum.org/about-the-met/policies-and-documents/open-access"
                )
                append(" Photography from ")
                appendLink("Unsplash.", url = "https://unsplash.com/@gskinner/collections")
            }
            val uriHandler = LocalUriHandler.current

            ClickableText(
                text,
                style = MaterialTheme.typography.bodyMedium,
                onClick = { offset ->
                    val url = text.getUrlAnnotations(offset, offset).firstOrNull()?.item?.url
                    println("$url")
                    url?.let {
                        uriHandler.openUri(it)
                    }
                }
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


@OptIn(ExperimentalTextApi::class)
@Composable
fun AnnotatedString.Builder.appendLink(str: String, url: String) =
    withStyle(linkStyle) {
        withAnnotation(UrlAnnotation(url)) {
            append(str)
        }
    }

val linkStyle
    @Composable get() = SpanStyle(
        color = accent1
    )