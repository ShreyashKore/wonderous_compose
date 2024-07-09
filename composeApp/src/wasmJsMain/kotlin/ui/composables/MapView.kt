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
    val zoomLevel = .05f

    val startScale = 60 / zoomLevel.toDouble()
    example.map.MapView(
        modifier = modifier,
        userAgent = "wasm agent",
        latitude = latLng.latitude,
        longitude = latLng.longitude,
        onMapViewClick = { lat, lng ->
            onMapClick?.invoke(Coordinate(lat, lng))
            onMapClick != null
        },
        startScale = startScale,
        consumeScroll = false
    )
}

const val MAP_DIV_ID = "map"
const val EVENT_TYPE_MAP_CLICK = "map_click_custom"

