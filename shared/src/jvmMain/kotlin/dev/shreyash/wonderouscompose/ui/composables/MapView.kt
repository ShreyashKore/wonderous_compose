package dev.shreyash.wonderouscompose.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import dev.shreyash.wonderouscompose.models.LatLng

@Composable
actual fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType
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