import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.Wonder
import models.Wonders

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            VerticalPager(Wonders.size) {
                InfoScreen(Wonders[it])
            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoScreen(
    wonder: Wonder
) {
    LazyColumn {
        item {
            KamelImage(
                asyncPainterResource("${wonder.imageIds.firstOrNull()}"),
                contentDescription = wonder.title,
                modifier = Modifier.height(200.dp)
            )
        }
        stickyHeader {
            InfoTitle(
                infoSection = InfoSection.Construction
            )
        }
        item {
            Text(
                wonder.historyInfo1
            )
            Text(
                wonder.historyInfo2
            )
            Box(Modifier.height(800.dp))
        }
    }

}


@Composable
fun InfoTitle(
    infoSection: InfoSection
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.Blue),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            Modifier.background(
                Color.Gray, shape = CutCornerShape(corner = CornerSize(5.dp))
            ).size(120.dp), contentAlignment = Alignment.TopCenter
        ) {
            Text(infoSection.title)
        }
        Box(
            modifier = Modifier.fillMaxWidth().background(Color.Green),
        )
    }
}

enum class InfoSection(val title: String) {
    FactsAndHistory("Facts & History"), Construction("Construction"), Location("Location")
}


expect fun getPlatformName(): String