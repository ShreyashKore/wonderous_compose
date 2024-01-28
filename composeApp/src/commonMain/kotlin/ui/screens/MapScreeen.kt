package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import models.GpsPosition
import ui.composables.BackButton
import ui.composables.MapType
import ui.composables.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    gpsPosition: GpsPosition,
    onBackClick: () -> Unit,
) = Box {
    MapView(
        modifier = Modifier.fillMaxSize(),
        gps = gpsPosition,
        title = "Map",
        mapType = MapType.Satellite
    )
    TopAppBar(
        title = {},
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.align(Alignment.TopCenter)
    )
}