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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.launch
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import platform.changeLanguage
import ui.composables.AppIconButton
import ui.composables.SimpleGrid
import ui.homeBtnImage
import ui.theme.fgColor
import ui.theme.greyStrong
import ui.theme.offWhite
import ui.theme.white
import ui.utils.filePainterResource
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.circleButtonsSemanticClose
import wonderouscompose.composeapp.generated.resources.compass_full
import wonderouscompose.composeapp.generated.resources.homeMenuButtonAbout
import wonderouscompose.composeapp.generated.resources.homeMenuButtonExplore
import wonderouscompose.composeapp.generated.resources.homeMenuButtonView
import wonderouscompose.composeapp.generated.resources.icon_close
import wonderouscompose.composeapp.generated.resources.icon_collection
import wonderouscompose.composeapp.generated.resources.icon_info
import wonderouscompose.composeapp.generated.resources.icon_timeline
import wonderouscompose.composeapp.generated.resources.language


@Composable
fun HomeMenu(
    data: Wonder,
    onDismiss: () -> Unit,
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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = greyStrong.copy(.4f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // AppHeader
            AppHeader(
                onClickClose = onDismiss,
                onChangeLanguage = {
                    try {
                        changeLanguage(it)
                        onDismiss()
                    } catch (e: NotImplementedError) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Changing language is currently only supported in Android!"
                            )
                        }
                    }
                },
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
                        text = stringResource(Res.string.homeMenuButtonExplore)
                    )
                    HorizontalDivider(
                        color = white.copy(.2f),
                        thickness = 2.dp
                    )
                    BottomButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("This screen is not implemented yet!")
                            }
                        },
                        icon = Res.drawable.icon_collection,
                        text = stringResource(Res.string.homeMenuButtonView)
                    )
                    HorizontalDivider(
                        color = white.copy(.2f),
                        thickness = 2.dp
                    )
                    BottomButton(
                        onClick = { isAboutDialogOpen = true },
                        icon = Res.drawable.icon_info,
                        text = stringResource(Res.string.homeMenuButtonAbout)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
//    }
    }
}


@Composable
fun AppHeader(
    onClickClose: () -> Unit,
    onChangeLanguage: (tag: String) -> Unit,
) {
    var showLanguagePopup by remember { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth().safeDrawingPadding().height(72.dp).padding(8.dp)) {
        AppIconButton(
            icon = Res.drawable.icon_close,
            contentDescription = stringResource(Res.string.circleButtonsSemanticClose),
            onClick = onClickClose,
        )
        Spacer(Modifier.weight(1f))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = greyStrong, contentColor = white
            ),
            onClick = {
                showLanguagePopup = !showLanguagePopup
            },
        ) {
            Text(stringResource(Res.string.language))
        }
        if (showLanguagePopup) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = { showLanguagePopup = false }
            ) {
                Column(
                    Modifier.clip(RoundedCornerShape(8.dp)).background(white).padding(8.dp)
                ) {
                    languageOptions.map {
                        TextButton(onClick = {
                            onChangeLanguage(it.value)
                            showLanguagePopup = false
                        }) {
                            Text(it.key)
                        }
                    }
                }
            }
        }
    }
}

val wondersWithCentralNullElement = Wonders.toMutableList<Wonder?>().apply {
    add(4, null)
}


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
                    modifier = Modifier.size(36.dp)
                )
            }
        else
            Image(
                filePainterResource(wonder.homeBtnImage),
                contentDescription = stringResource(wonder.title),
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


@Composable
private fun BottomButton(
    onClick: () -> Unit,
    icon: DrawableResource,
    text: String,
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

private val languageOptions = mutableMapOf(
    "English" to "en",
    "हिंदी" to "hi",
    "中文" to "zh"
)