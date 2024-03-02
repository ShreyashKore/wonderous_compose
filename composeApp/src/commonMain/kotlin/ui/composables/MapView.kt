package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import models.LatLng

@Composable
expect fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    title: String,
    parentScrollEnableState: MutableState<Boolean> = mutableStateOf(true),
    zoomLevel: Float = 10f,
    mapType: MapType = MapType.Normal,
)

enum class MapType {
    Normal, Satellite,
}