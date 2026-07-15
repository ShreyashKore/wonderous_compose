package dev.shreyash.wonderouscompose.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.HtmlElementView
import dev.shreyash.wonderouscompose.js.jsArrayOf
import dev.shreyash.wonderouscompose.openlayersmap.Map
import dev.shreyash.wonderouscompose.models.LatLng
import dev.shreyash.wonderouscompose.openlayersmap.Feature
import dev.shreyash.wonderouscompose.openlayersmap.LonLat
import dev.shreyash.wonderouscompose.openlayersmap.OSM
import dev.shreyash.wonderouscompose.openlayersmap.Point
import dev.shreyash.wonderouscompose.openlayersmap.TileLayer
import dev.shreyash.wonderouscompose.openlayersmap.TileLayerOptions
import dev.shreyash.wonderouscompose.openlayersmap.Vector
import dev.shreyash.wonderouscompose.openlayersmap.VectorOptions
import dev.shreyash.wonderouscompose.openlayersmap.VectorSource
import dev.shreyash.wonderouscompose.openlayersmap.VectorSourceOptions
import dev.shreyash.wonderouscompose.openlayersmap.VectorStyle
import dev.shreyash.wonderouscompose.openlayersmap.View
import dev.shreyash.wonderouscompose.openlayersmap.ViewOptions
import dev.shreyash.wonderouscompose.openlayersmap.XYZ
import dev.shreyash.wonderouscompose.openlayersmap.XYZOptions
import dev.shreyash.wonderouscompose.openlayersmap.fromLonLat
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType
) {
    val map = remember { Map() }
    val adjustedZoomLevel = zoomLevel * 5f

    HtmlElementView(
        modifier = modifier,
        factory = {
            document.createElement("div").apply {
                id = "map"
            }  as HTMLDivElement
        },
        update = {
            val center = fromLonLat(LonLat(latLng.longitude, latLng.latitude))
            val tileLayer = TileLayer(TileLayerOptions(source = mapType.toOlSource()))
            val vectorLayerForMaker = Vector(
                VectorOptions(
                    source = VectorSource(VectorSourceOptions(feature = Feature(Point(center)))),
                    style = DEFAULT_MARKER_STYLE,
                ),
            )
            val view = View(
                ViewOptions(
                    center = center,
                    zoom = adjustedZoomLevel,
                    maxZoom = MAX_ZOOM
                )
            )
            map.apply {
                setTarget("map")
                setLayers(jsArrayOf(tileLayer, vectorLayerForMaker))
                setView(view)
            }
        },
    )
}

/**
 * Converts map type to OpenLayers source
 */
private fun MapType.toOlSource() = when (this) {
    MapType.Normal -> OSM()
    MapType.Satellite -> XYZ(
        XYZOptions(
            attributions = "Tiles © <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>",
            url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
        )
    )
}

private val DEFAULT_MARKER_STYLE: VectorStyle = VectorStyle(
    circleFillColor = "blue",
    circleRadius = 10f,
    circleStrokeColor = "white",
)

/**
 * Satellite map view doesn't provide imagery beyond this zoom level
 */
private const val MAX_ZOOM = 18f