package ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import models.GpsPosition

@Composable
actual fun MapView(
    modifier: Modifier,
    gps: GpsPosition,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Map Not implemented")
        Text("Lat: ${gps.latitude}, Lng: ${gps.longitude}")
    }
}