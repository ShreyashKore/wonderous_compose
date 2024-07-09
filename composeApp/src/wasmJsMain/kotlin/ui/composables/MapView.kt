package ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.hamama.kwhi.HtmlView
import models.LatLng

@Composable
actual fun MapView(
    modifier: Modifier,
    latLng: LatLng,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType
) {
    if (mapType == MapType.Satellite) {
        HtmlView(
            modifier = modifier,
            factory = {
                createElement("iframe").apply {
                    setAttribute("width", "100%")
                    setAttribute("height", "100%")
                    setAttribute(
                        "src",
                        "//umap.openstreetmap.fr/en/map/my_1030003#7/${latLng.latitude}/${latLng.longitude}?scaleControl=false&miniMap=false&scrollWheelZoom=false&zoomControl=true&editMode=disabled&moreControl=true&searchControl=null&tilelayersControl=null&embedControl=null&datalayersControl=true&onLoadPanel=undefined&captionBar=false&captionMenus=true"
                    )
                }
            }
        )
        return
    }

    HtmlView(
        modifier = modifier,
        factory = {
            createElement("div").apply {
                id = "map"
            }
        },
        update = {

//            map()

//            setupMap(L, "map", latLng.latitude, latLng.longitude, 260 * zoomLevel, 13f)
        },
    )
}