package openlayers

external interface Layer

@JsModule("ol/layer/Tile.js")
@JsNonModule
external class TileLayer(options: TileLayerOptions) : Layer

external interface TileLayerOptions {
    var source: XYZ
}

fun TileLayerOptions(source: XYZ): TileLayerOptions = js("({ 'source': source })")


@JsModule("ol/layer/Vector.js")
@JsNonModule
external class Vector(options: VectorOptions) : Layer

fun VectorOptions(
    source: VectorSource? = null,
    style: VectorStyle? = null
): VectorOptions = js(
    """({
        source: source,
        style: style
    })
    """
)

external interface VectorOptions {
    var source: VectorSource
    var style: VectorStyle
}

fun VectorStyle(
    circleFillColor: String,
    circleRadius: Float,
    circleStrokeColor: String
): VectorStyle = js(
    """({
        'circle-fill-color': circleFillColor,
        'circle-radius': circleRadius,
        'circle-stroke-color': circleStrokeColor
    })"""
)

external interface VectorStyle {
    var circleFillColor: String
    var circleRadius: Float
    var circleStrokeColor: String
}