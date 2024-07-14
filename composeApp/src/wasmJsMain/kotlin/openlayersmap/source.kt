package openlayersmap


@JsModule("ol/source/XYZ.js")
open external class XYZ(options: JsAny) : JsAny

external interface XYZOptions : JsAny {
    var attributions: String
    var url: String
}

fun XYZOptions(attributions: String? = "", url: String? = ""): XYZOptions = js(
    """({ attributions: attributions,  url: url })"""
)

@JsModule("ol/source/OSM.js")
external class OSM : XYZ


@JsModule("ol/source/Vector.js")
external class VectorSource(options: VectorSourceOptions) : JsAny

fun VectorSourceOptions(feature: Feature): VectorSourceOptions = js(
    """({ features: [feature] })"""
)

external interface VectorSourceOptions : JsAny {
    var features: JsArray<Feature>
}