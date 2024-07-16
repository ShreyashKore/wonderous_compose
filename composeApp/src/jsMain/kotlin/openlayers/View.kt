package openlayers

@JsModule("ol/View.js")
@JsNonModule
external class View(options: ViewOptions)

external interface ViewOptions {
    var lat: Double
    var lng: Double
    var zoom: Float
    var maxZoom: Float
}

fun ViewOptions(center: Coordinate? = null, zoom: Float = 10f, maxZoom: Float = 10f): ViewOptions =
    js(
        """
    ({
        center: center,
        zoom: zoom,
        maxZoom: maxZoom,
    })
    """
    )