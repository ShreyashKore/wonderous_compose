@JsModule("ol/Map.js")
external class Map(options: JsAny? = definedExternally) {
    fun addLayer(layer: Vector)
}

external interface MapOptions : JsAny {
    var target: String
    var layers: JsArray<TileLayer>?
    var view: View?
}

fun MapOptions(
    target: String? = null,
    layers: JsArray<TileLayer>? = null,
    view: View? = null
): MapOptions =
    js(
        """({
        target: target,
        layers: layers,
        view: view
    })
    """
    )

@JsModule("ol/View.js")
external class View(options: JsAny) : JsAny


fun ViewOptions(lat: Double, lng: Double, zoom: Float, maxZoom: Float): JsAny = js(
    """
    ({
        center: [lat, lng],
        zoom: zoom,
        maxZoom: maxZoom,
    })
    """
)

@JsModule("ol/source/XYZ.js")
external class XYZ(options: JsAny) : JsAny

external interface XYZOptions : JsAny {
    var attributions: String
    var url: String
}

fun XYZOptions(attributions: String? = "", url: String? = ""): XYZOptions = js(
    """({ attributions: attributions,  url: url })"""
)

@JsModule("ol/layer/Tile.js")
external class TileLayer(options: TileLayerOptions) : JsAny

external interface TileLayerOptions {
    var source: XYZ
}

fun TileLayerOptions(source: XYZ): TileLayerOptions = js(
    """
    ({
        "source": source
    })
    """
)


@JsModule("ol/layer/Vector.js")
external class Vector(options: JsAny) : JsAny

fun VectorOptions(
    source: VectorSource? = null,
    style: JsAny? = null
): JsAny = js(
    """({
        source: source,
        style: style
    })
    """
)

fun style(): JsAny = js(
    """({
            'circle-fill-color': 'blue',
            'circle-radius': 10,
            'circle-stroke-color': 'white',
        })"""
)

@JsModule("ol/source/Vector.js")
external class VectorSource(options: JsAny) : JsAny

fun VectorSourceOptions(feature: Feature): JsAny = js(
    """({
        features: [feature]
    })
"""
)

@JsModule("ol/Feature.js")
external class Feature(options: JsAny) : JsAny

@JsModule("ol/geom/Point.js")
external class Point(options: JsAny?) : JsAny

fun PointOptions(x: Double, y: Double): JsAny = js("([x,y])")

fun <T : JsAny> jsArrayOf(value: JsAny): JsArray<T> = js("[value]")


fun map(
    target: String,
    lat: Double,
    lng: Double,
    zoomLevel: Float,
    maxZoomLevel: Float
) {
    Map(
        MapOptions(
            target = target,
            layers = jsArrayOf(
                TileLayer(
                    TileLayerOptions(
                        source = XYZ(
                            XYZOptions(
                                attributions = "Tiles © <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>",
                                url = "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
                            )
                        )
                    )
                )
            ),
            view = View(
                ViewOptions(
                    lat = lat,
                    lng = lng,
                    zoom = zoomLevel,
                    maxZoom = maxZoomLevel
                )
            )
        )
    ).also {
        it.addLayer(
            Vector(
                VectorOptions(
                    source = VectorSource(
                        options = VectorSourceOptions(
                            feature = Feature(
                                Point(
                                    PointOptions(
                                        lat,
                                        lng
                                    )
                                )
                            )
                        )
                    ),
                    style = style()
                ),
            )
        )
    }
}