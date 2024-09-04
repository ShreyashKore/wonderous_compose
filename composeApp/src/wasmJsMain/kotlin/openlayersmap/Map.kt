package openlayersmap

@JsModule("ol/Map.js")
external class Map(options: MapOptions? = definedExternally) : JsAny {
    fun setTarget(target: String)
    fun addLayer(layer: Layer)
    fun updateSize()
    fun setLayers(layers: JsArray<Layer>)
    fun setView(view: View)
}

@JsModule("ol/ol.css")
external val olCss: JsAny

external interface MapOptions : JsAny {
    var target: String
    var layers: JsArray<Layer>?
    var view: View?
}

fun MapOptions(
    target: String? = null, layers: JsArray<Layer>? = null, view: View? = null,
): MapOptions = js(
    """({
        target: target,
        layers: layers,
        view: view
    })
    """
)


