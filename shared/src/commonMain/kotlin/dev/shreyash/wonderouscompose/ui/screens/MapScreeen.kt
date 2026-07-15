package dev.shreyash.wonderouscompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.shreyash.wonderouscompose.models.LatLng
import dev.shreyash.wonderouscompose.ui.composables.BackButton
import dev.shreyash.wonderouscompose.ui.composables.MapType
import dev.shreyash.wonderouscompose.ui.composables.MapView
import dev.shreyash.wonderouscompose.ui.theme.black

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
    MapView(
        modifier = Modifier.fillMaxSize(),
        latLng = latLng,
        title = "Map",
        mapType = MapType.Satellite
    )
}