package openlayers

import kotlin.js.collections.JsArray

@JsNonModule
@JsModule("ol/Map.js")
external class Map {
    fun setTarget(target: String)
    fun addLayer(layer: Layer)
    fun updateSize()

    @OptIn(ExperimentalJsCollectionsApi::class)
    fun setLayers(layers: JsArray<Layer>)
    fun setView(view: View)
}

external interface MapOptions {
    var target: String

    @OptIn(ExperimentalJsCollectionsApi::class)
    var layers: JsArray<Layer>?
    var view: View?
}

@OptIn(ExperimentalJsCollectionsApi::class)
fun MapOptions(
    target: String? = null, layers: JsArray<Layer>? = null, view: View? = null
): MapOptions = js(
    """({
        target: target,
        layers: layers,
        view: view
    })
    """
)


