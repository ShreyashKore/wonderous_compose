package ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamama.kwhi.HtmlView
import external.L
import models.LatLng

@Composable
fun Test() {
    val latLng = LatLng(19.0760, 72.8777)
    val latLngOffset = latLng/*.shifted(.5, .5)*/
    val zoomLevel = 260
    HtmlView(
        modifier = Modifier.width(400.dp).height(400.dp),
        factory = {
            createElement("div").apply {
                id = "map"
                setAttribute("height", "100%")
            }
        },
        update = {
            val center = arrayOf(latLng.latitude, latLng.longitude)
            val offset = arrayOf(latLngOffset.latitude, latLngOffset.longitude)
            val map = L.map("map").setView(offset, zoomLevel)
            L.tileLayer(
                "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
                mapOf(
                    "maxZoom" to 13,
                    "attribution" to "&copy; <a href=\"http://www.openstreetmap.org/copyright\">OpenStreetMap</a>"
                )
            ).addTo(map)
            L.marker(center).addTo(map)
            Unit
        },
    )
}