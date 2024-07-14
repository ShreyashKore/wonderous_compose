package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import models.LatLng
import ui.composables.BackButton
import ui.composables.Coordinate
import ui.composables.MapType
import ui.composables.MapView
import ui.theme.black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    latLng: LatLng,
    onBackClick: () -> Unit,
) = Column {
    TopAppBar(
        title = {},
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = black),
    )

    var selectedLocation by remember { mutableStateOf(Coordinate(latLng.latitude, latLng.longitude)) }
    TextButton(
        onClick = {},
        content = { Text(text = "HelloMM") },
        enabled = selectedLocation.latitude != latLng.latitude,
    )
    MapView(
        modifier = Modifier.fillMaxSize(),
        latLng = latLng,
        selectedPos = selectedLocation,
        title = "Map",
        mapType = MapType.Satellite,
        onMapClick = {
            selectedLocation = it
        }
    )
}