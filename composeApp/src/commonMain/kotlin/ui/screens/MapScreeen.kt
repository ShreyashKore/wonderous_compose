package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import models.GpsPosition
import ui.composables.BackButton
import ui.composables.MapType
import ui.composables.MapView
import ui.theme.black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    gpsPosition: GpsPosition,
    onBackClick: () -> Unit,
) = Column {
    TopAppBar(
        title = {},
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = black),
    )
    MapView(
        modifier = Modifier.fillMaxSize(),
        gps = gpsPosition,
        title = "Map",
        mapType = MapType.Satellite
    )
}