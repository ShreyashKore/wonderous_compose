package leaflet

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
            L.marker([lat, long]).addTo(map)
        """
    )
}