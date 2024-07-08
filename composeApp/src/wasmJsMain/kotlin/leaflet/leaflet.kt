package leaflet

import ui.composables.EVENT_TYPE_MAP_CLICK
import ui.composables.MAP_DIV_ID

@JsModule("leaflet")
external val L: Leaflet

external class Leaflet


fun setupMap(
    L: Leaflet,
    id: String,
    lat: Double,
    long: Double,
    zoomLevel: Float,
    maxZoomLevel: Float
) {
    js(
        """          
            const map = L.map(id).setView([lat, long], zoomLevel)
            L.tileLayer(
                "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
                {
                    maxZoom : maxZoomLevel,
                    attribution : "&copy; <a href=\"http://www.openstreetmap.org/copyright\">OpenStreetMap</a>"
                }
            ).addTo(map)
            
            let currentMarker = null; // Variable to store the current marker

            function marker_change_custom(event) {
                const detail = event.detail;
                const lat = detail.lat;
                const long = detail.long;
                if (currentMarker) {
                    map.removeLayer(currentMarker);
                }
                currentMarker = L.marker([lat, long]).addTo(map)
            }
            
            function map_marker_clear_custom(event) {
                if (currentMarker) {
                    map.removeLayer(currentMarker);
                }
            }
            
            function onMapClick(e) {
                const mapDiv = document.getElementById('$MAP_DIV_ID');
                if (mapDiv) {
                     const event = new CustomEvent("$EVENT_TYPE_MAP_CLICK", {
                        detail: {
                            latitude: e.latlng.lat,
                            longitude: e.latlng.lng
                        }
                    });
                    
                    mapDiv.dispatchEvent(event);
                }
            }
            map.on('click', onMapClick);
            
            document.getElementById('$MAP_DIV_ID').addEventListener('map_marker_change', marker_change_custom);
            document.getElementById('$MAP_DIV_ID').addEventListener('map_marker_clear', map_marker_clear_custom);
        """
    )
}

val jsCreateLatLng: (Double, Double) -> JsAny = js("function(lat, long) { return { lat: lat, long: long }; }")
