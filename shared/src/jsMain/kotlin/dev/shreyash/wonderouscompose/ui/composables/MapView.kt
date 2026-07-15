package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hamama.kwhi.HtmlView
import js.jsArrayOf
import models.LatLng
import openlayers.Feature
import openlayers.LonLat
import openlayers.OSM
import openlayers.Point
import openlayers.TileLayer
import openlayers.TileLayerOptions
import openlayers.Vector
import openlayers.VectorOptions
import openlayers.VectorSource
import openlayers.VectorSourceOptions
import openlayers.VectorStyle
import openlayers.View
import openlayers.ViewOptions
import openlayers.XYZ
import openlayers.XYZOptions
import openlayers.fromLonLat
import openlayers.olCss

@OptIn(ExperimentalJsCollectionsApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType,
) {
    val map = remember { openlayers.Map() }
    val adjustedZoomLevel = zoomLevel * 5f

    HtmlView(
        modifier = modifier,
        factory = {
            createElement("div").apply {
                id = "map"
            }
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

// Referring style so that it is not removed by the compiler ??
val styles = olCss