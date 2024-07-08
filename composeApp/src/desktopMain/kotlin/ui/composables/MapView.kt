package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import models.LatLng

@Composable
actual fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    selectedPos: Coordinate,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType,
    onMapClick: (Coordinate) -> Unit,
) {
    example.map.MapView(
        modifier = modifier,
        userAgent = "ComposeMapViewExample",
        latitude = latLng.latitude,
        longitude = latLng.longitude,
        startScale = 60 / zoomLevel.toDouble(),
        consumeScroll = false
    )
}