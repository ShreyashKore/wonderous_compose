package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ImagePaths
import utils.StringUtils.getYrSuffix
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun WonderEvents(
    wonder: Wonder,
    navigateToTimeLine: () -> Unit,
) = BoxWithConstraints {
    val wonderEvents = wonder.events

    val bgHeight = maxHeight * 0.6f
    val sheetHeight = maxHeight * 0.4f

    BottomSheetScaffold(
        sheetPeekHeight = sheetHeight,
        sheetContent = {
            LazyColumn {
                items(wonderEvents.toList()) { item ->
                    EventCard(
                        year = item.first,
                        text = item.second,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                    )
                }
                item {
                    Spacer(Modifier.height(500.dp))
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.height(bgHeight).padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painterResource("images/chichen_itza/flattened.jpg"),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
                        .drawWithContent {
                            val gradient =
                                Brush.verticalGradient(listOf(Color.Transparent, Color.Black))
                            drawContent()
                            drawRect(gradient)
                        },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.BottomCenter,
                )
                Text(
                    wonder.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Box(Modifier.background(Color.Red).height(120.dp).fillMaxWidth())
        }
    }

    IconButton(
        modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
        onClick = navigateToTimeLine,
    ) {
        Box(Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.surface)) {
            Icon(
                painterResource("${ImagePaths.common}/tab-timeline.png"),
                contentDescription = "Open Timeline",
                modifier = Modifier.padding(8.dp).size(28.dp),
            )
        }
    }
    Box(
        Modifier
            .zIndex(10f)
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        Button(
            onClick = navigateToTimeLine,
            modifier = Modifier
                .padding(bottom = 100.dp, start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth()
        ) {
            Text("OPEN GLOBAL TIMELINE")
        }
    }

}

@Composable
private fun EventCard(
    year: Int,
    text: String,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.Gray,
        contentColor = Color.White,
        elevation = 4.dp,
    ) {
        Row(Modifier.height(IntrinsicSize.Min).padding(8.dp)) {
            Column(Modifier.width(80.dp)) {
                Text("${year.absoluteValue}")
                Text(getYrSuffix(year))
            }
            Divider(Modifier.padding(horizontal = 8.dp).width(2.dp).fillMaxHeight())
            Text(text)
        }
    }
}


/*
val padding = 300.dp
val startYear = -3000
val endYear = 2200
val totalYearRange = endYear - startYear

val totalYearRangeInPx = totalYearRange * 200

val scrollState = rememberScrollState()
val scope = rememberCoroutineScope()

fun jumpToYear(year: Int, animate: Boolean = false) {
    val yearRatio = (year - startYear) / totalYearRange.toFloat()
    val maxScroll = 4000f // Calculate
    val newPos = yearRatio * maxScroll
    scope.launch {
        if (animate) scrollState.animateScrollTo(newPos.toInt())
        else scrollState.scrollTo(newPos.toInt())
    }
}


    Box(Modifier.fillMaxWidth().verticalScroll(scrollState)) {
        Row {
            Box(Modifier.background(Color.Red))
            Box(Modifier.background(Color.Blue))
            Box(Modifier.background(Color.Yellow))
            Box(Modifier.background(Color.Green))
        }
    }
*/