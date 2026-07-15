package openlayers

import kotlin.js.collections.JsArray

@JsModule("ol/source/XYZ.js")
@JsNonModule
open external class XYZ(options: dynamic)

external interface XYZOptions {
    var attributions: String
    var url: String
}

fun XYZOptions(attributions: String? = "", url: String? = ""): XYZOptions = js(
    "({ attributions: attributions,  url: url })"
)

@JsModule("ol/source/OSM.js")
@JsNonModule
external class OSM : XYZ


@JsModule("ol/source/Vector.js")
@JsNonModule
external class VectorSource(options: VectorSourceOptions)

fun VectorSourceOptions(feature: Feature): VectorSourceOptions = js(
    "({ features: [feature] })"
)

external interface VectorSourceOptions {
    @OptIn(ExperimentalJsCollectionsApi::class)
    var features: JsArray<Feature>
}