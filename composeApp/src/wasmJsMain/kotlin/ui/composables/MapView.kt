package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.hamama.kwhi.HtmlView
import leaflet.L
import leaflet.setupMap
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
    HtmlView(
        modifier = modifier,
        factory = {
            createElement("div").apply {
                id = "map"
            }
        },
        update = {
            setupMap(L, "map", gps.latitude, gps.longitude, 260 * zoomLevel, 13f)
        },
    )
}