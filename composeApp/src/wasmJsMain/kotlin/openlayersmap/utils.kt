package openlayersmap


@JsModule("ol/Feature.js")
external class Feature(options: JsAny) : JsAny

@JsModule("ol/geom/Point.js")
external class Point(options: JsAny?) : JsAny

// OpenLayers uses LonLat instead of LatLng
fun LonLat(lon: Double, lat: Double): LonLat = js("[lon,lat]")
external interface LonLat : JsAny