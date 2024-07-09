package ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hamama.kwhi.HtmlView
import map
import models.LatLng

@Composable
fun Test() {
    val latLng = LatLng(19.0760, 72.8777)
//    val latLngOffset = latLng/*.shifted(.5, .5)*/
//    val zoomLevel = 260
    HtmlView(
        modifier = Modifier.width(400.dp).height(400.dp),
        factory = {
            createElement("div").apply {
                id = "map"
            }
        },
        update = {
            map(
                "map",
                latLng.longitude,
                latLng.latitude,
                10f,
                10f,
            )
        },
    )
}