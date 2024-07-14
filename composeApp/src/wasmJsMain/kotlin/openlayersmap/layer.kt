package openlayersmap

external interface Layer : JsAny

@JsModule("ol/layer/Tile.js")
external class TileLayer(options: TileLayerOptions) : Layer

external interface TileLayerOptions {
    var source: XYZ
}

fun TileLayerOptions(source: XYZ): TileLayerOptions = js("({ 'source': source })")


@JsModule("ol/layer/Vector.js")
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

external interface VectorOptions : JsAny {
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

external interface VectorStyle : JsAny {
    var circleFillColor: String
    var circleRadius: Float
    var circleStrokeColor: String
}