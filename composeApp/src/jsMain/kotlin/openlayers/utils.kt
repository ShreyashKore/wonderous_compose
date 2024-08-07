package openlayers


@JsModule("ol/Feature.js")
@JsNonModule
external class Feature(options: dynamic)

@JsModule("ol/geom/Point.js")
@JsNonModule
external class Point(options: dynamic)

// OpenLayers uses LonLat instead of LatLng
fun LonLat(lon: Double, lat: Double): LonLat = js("[lon,lat]")
external interface LonLat