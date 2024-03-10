package ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.composables.AppIconButton
import ui.composables.SimpleGrid
import ui.homeBtn
import ui.theme.fgColor
import ui.theme.offWhite
import ui.theme.white
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.compass_full
import wonderouscompose.composeapp.generated.resources.icon_close
import wonderouscompose.composeapp.generated.resources.icon_collection
import wonderouscompose.composeapp.generated.resources.icon_info
import wonderouscompose.composeapp.generated.resources.icon_timeline

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeMenu(
    data: Wonder,
    onPressBack: () -> Unit,
    onChangeWonder: (Wonder) -> Unit,
    openTimeline: () -> Unit,
    openCollection: () -> Unit,
    modifier: Modifier = Modifier,
) = BoxWithConstraints(modifier) {
    val btnSize = (maxWidth / 4).coerceIn(60.dp, 100.dp)
    var isAboutDialogOpen by remember {
        mutableStateOf(false)
    }


    if (isAboutDialogOpen) {
        AboutApp(onDismissRequest = { isAboutDialogOpen = false })
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // AppHeader
        AppHeader(
            onClickClose = onPressBack,
            onToggleLanguage = { /* TODO - localization */ },
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 48.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            // Wonder Buttons Grid
            WonderBtnsGrid(
                currentWonder = data,
                onSelectWonder = onChangeWonder,
                btnSize = btnSize,
                modifier = Modifier
                    .width(btnSize * 3)
                    .height(btnSize * 3)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Bottom buttons
            Column(
                modifier = Modifier.widthIn(max = 450.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomButton(
                    onClick = openTimeline,
                    icon = Res.drawable.icon_timeline,
                    text = "Explore the timeline"
                )
                HorizontalDivider(modifier = Modifier.height(2.dp), color = white.copy(.2f))
                BottomButton(
                    onClick = openCollection,
                    icon = Res.drawable.icon_collection,
                    text = "View your collections"
                )
                HorizontalDivider(modifier = Modifier.height(2.dp), color = white.copy(.2f))
                BottomButton(
                    onClick = { isAboutDialogOpen = true },
                    icon = Res.drawable.icon_info,
                    text = "About this app"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppHeader(
    onClickClose: () -> Unit,
    onToggleLanguage: () -> Unit,
) {
    Row(Modifier.fillMaxWidth().safeDrawingPadding().height(72.dp).padding(8.dp)) {
        AppIconButton(
            icon = Res.drawable.icon_close,
            contentDescription = "Close",
            onClick = onClickClose,
        )
        Spacer(Modifier.weight(1f))
        AppIconButton(
            icon = Res.drawable.icon_close,
            contentDescription = "Change Language",
            onClick = onToggleLanguage,
        )
    }
}

val wondersWithCentralNullElement = Wonders.toMutableList<Wonder?>().apply {
    add(4, null)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderBtnsGrid(
    currentWonder: Wonder,
    onSelectWonder: (Wonder) -> Unit,
    btnSize: Dp,
    modifier: Modifier = Modifier,
) = SimpleGrid(
    modifier = modifier,
    columnCount = 3,
) {
    val shape = RoundedCornerShape(10.dp)

    wondersWithCentralNullElement.map { wonder ->
        if (wonder == null)
        // Center icon
            Box(
                Modifier.size(btnSize).padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painterResource(Res.drawable.compass_full),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        else
            Image(
                painterResource(wonder.homeBtn),
                contentDescription = wonder.title,
                modifier = Modifier
                    .size(btnSize)
                    .padding(8.dp)
                    .clip(shape)
                    .clickable { onSelectWonder(wonder) }
                    .background(wonder.fgColor)
                    .run {
                        if (currentWonder == wonder) border(
                            width = 6.dp,
                            color = offWhite,
                            shape = shape
                        ) else this
                    }
            )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomButton(
    onClick: () -> Unit,
    icon: DrawableResource,
    text: String
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = white,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = white,
            )
        }
    }
}
