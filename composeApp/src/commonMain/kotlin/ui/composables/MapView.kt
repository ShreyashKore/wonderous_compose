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
    selectedPos: Coordinate,
    title: String,
    parentScrollEnableState: MutableState<Boolean> = mutableStateOf(true),
    zoomLevel: Float = 10f,
    mapType: MapType = MapType.Normal,
    onMapClick: (Coordinate) -> Unit = { _ -> },
)

enum class MapType {
    Normal, Satellite,
}

data class Coordinate(val latitude: Double, val longitude: Double) {
    fun isLocationValid(): Boolean {
        return latitude != 0.0 && longitude != 0.0
    }
}