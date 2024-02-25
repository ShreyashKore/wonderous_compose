package ui.composables


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.hamama.kwhi.HtmlView
import external.L
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
            val map = L.map("map").setView(arrayOf(gps.latitude, gps.longitude), zoomLevel * 260)
            L.tileLayer(
                "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
                mapOf(
                    "maxZoom" to 13,
                    "attribution" to "&copy; <a href=\"http://www.openstreetmap.org/copyright\">OpenStreetMap</a>"
                )
            ).addTo(map)
            L.marker(arrayOf(gps.latitude, gps.longitude)).addTo(map)
            Unit
        },
    )
}