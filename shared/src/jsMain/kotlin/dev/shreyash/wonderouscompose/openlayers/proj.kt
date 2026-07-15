@file:JsModule("ol/proj.js")
@file:JsNonModule

package openlayers

external interface Coordinate

external fun fromLonLat(
    coordinate: dynamic = definedExternally,
    projection: dynamic = definedExternally
): Coordinate