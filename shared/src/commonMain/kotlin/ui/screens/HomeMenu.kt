package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.AppIcons
import ui.composables.AppIconButton
import ui.homeBtn
import ui.theme.fgColor
import ui.theme.offWhite
import ui.theme.white

@Composable
fun HomeMenu(
    data: Wonder,
    onPressBack: () -> Unit,
    onChangeWonder: (Wonder) -> Unit,
    modifier: Modifier = Modifier,
) = BoxWithConstraints {
    val btnSize = (maxWidth / 5).coerceIn(60.dp, 100.dp)

    Column(modifier) {
        // AppHeader
        AppHeader(
            onClickClose = onPressBack,
            onToggleLanguage = { /* TODO - localization */ },
        )

        // Content
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                WonderBtnsGrid(
                    currentWonder = data,
                    onSelectWonder = onChangeWonder,
                    btnSize = btnSize,
                )
                Spacer(modifier = Modifier.height(24.dp).background(Color.Green))
                BottomButtons()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AppHeader(
    onClickClose: () -> Unit,
    onToggleLanguage: () -> Unit,
) {
    Row(Modifier.fillMaxWidth().height(72.dp).padding(8.dp)) {
        AppIconButton(
            iconPath = AppIcons.Close,
            contentDescription = "Close",
            onClick = onClickClose,
        )
        Spacer(Modifier.weight(1f))
        AppIconButton(
            iconPath = AppIcons.Info,
            contentDescription = "Change Language",
            onClick = onToggleLanguage,
        )
    }
}

val modifiedWondersList = Wonders.toMutableList<Wonder?>().apply {
    add(4, null)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderBtnsGrid(
    currentWonder: Wonder,
    onSelectWonder: (Wonder) -> Unit,
    btnSize: Dp
) = LazyVerticalGrid(
    modifier = Modifier.wrapContentWidth(),
    columns = GridCells.Fixed(3),
    userScrollEnabled = false,
    horizontalArrangement = Arrangement.Center
) {
    val shape = RoundedCornerShape(10.dp)

    items(
        modifiedWondersList
    ) { wonder ->

        if (wonder == null)
            Box(
                Modifier.padding(8.dp).size(btnSize),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painterResource("images/common/compass_full.xml"),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        else
            IconButton(
                onClick = { onSelectWonder(wonder) }
            ) {
                Image(
                    painterResource(wonder.homeBtn),
                    contentDescription = wonder.title,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(btnSize)
                        .clip(shape)
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
}


@Composable
fun BottomButtons(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomButton(
            onClick = { /* Handle Timeline Button Click */ },
            icon = AppIcons.Timeline,
            text = "Explore the timeline"
        )

        Divider(modifier = Modifier.height(1.dp), color = Color.Gray)

        BottomButton(
            onClick = { /* Handle Collection Button Click */ },
            icon = AppIcons.Collection,
            text = "View your collections"
        )

        Divider(modifier = Modifier.height(1.dp), color = Color.Gray)

        BottomButton(
            onClick = { /* Handle Info Button Click */ },
            icon = AppIcons.Info,
            text = "About this app"
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomButton(
    onClick: () -> Unit,
    icon: String,
    text: String
) {
    TextButton(
        onClick = onClick,
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
