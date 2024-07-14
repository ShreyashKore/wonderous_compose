@file:JsModule("ol/proj.js")

package openlayersmap

external interface Coordinate : JsAny

external fun fromLonLat(
    coordinate: JsAny? = definedExternally,
    projection: JsAny = definedExternally
): Coordinate