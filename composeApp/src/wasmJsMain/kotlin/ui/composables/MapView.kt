package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hamama.kwhi.HtmlView
import leaflet.L
import leaflet.jsCreateLatLng
import leaflet.setupMap
import models.LatLng
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.Element
import org.w3c.dom.events.Event

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
//    if (mapType == MapType.Satellite) {
//        HtmlView(
//            modifier = modifier,
//            factory = {
//                createElement("iframe").apply {
//                    setAttribute("width", "100%")
//                    setAttribute("height", "100%")
//                    setAttribute(
//                        "src",
//                        "//umap.openstreetmap.fr/en/map/my_1030003#7/${latLng.latitude}/${latLng.longitude}?scaleControl=false&miniMap=false&scrollWheelZoom=false&zoomControl=true&editMode=disabled&moreControl=true&searchControl=null&tilelayersControl=null&embedControl=null&datalayersControl=true&onLoadPanel=undefined&captionBar=false&captionMenus=true"
//                    )
//                }
//            }
//        )
//        return
//    }
//
//    val document = remember {
//        mutableStateOf<Element?>(null)
//    }
//
//    LaunchedEffect(selectedPos) {
//        val doc = document.value
//
//        val markerPos = selectedPos
//
//        if (doc != null && markerPos != null) {
//            val latitude = markerPos.latitude
//            val longitude = markerPos.longitude
//            val detail = jsCreateLatLng(latitude, longitude)
//            val detailsInit = CustomEventInit(detail = detail)
//
//            doc.dispatchEvent(event = CustomEvent("map_marker_change", detailsInit))
//        }
//        if (markerPos == null) {
//            doc?.dispatchEvent(event = Event("map_marker_clear"))
//        }
//    }
//
//    HtmlView(
//        modifier = modifier,
//        factory = {
//            createElement(MAP_DIV_ID).apply {
//                addEventListener(EVENT_TYPE_MAP_CLICK) { event ->
//                    val mapClickEvent = event as CustomMapClickEvent
//                    onMapClick?.invoke(
//                        Coordinate(
//                            latitude = mapClickEvent.detail.latitude,
//                            longitude = mapClickEvent.detail.longitude,
//                        )
//                    )
//                }
//                document.value = this
//                id = "map"
//            }
//        },
//        update = {
//            setupMap(L, "map", latLng.latitude, latLng.longitude, 260 * zoomLevel, 13f)
//        },
//    )
}

external interface LatLngEventDetail {
    val latitude: Double
    val longitude: Double
}

external interface CustomMapClickEvent {
    val detail: LatLngEventDetail
}

const val MAP_DIV_ID = "map"
const val EVENT_TYPE_MAP_CLICK = "map_click_custom"

