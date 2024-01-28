package ui.composables


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import models.GpsPosition
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKCoordinateRegionMakeWithDistance
import platform.MapKit.MKMapTypeSatellite
import platform.MapKit.MKMapTypeStandard
import platform.MapKit.MKMapView
import platform.MapKit.MKPointAnnotation

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    gps: GpsPosition,
    title: String,
    parentScrollEnableState: MutableState<Boolean>,
    zoomLevel: Float,
    mapType: MapType
) {
    val location = CLLocationCoordinate2DMake(gps.latitude, gps.longitude)
    val annotation = remember {
        MKPointAnnotation(
            location,
            title = null,
            subtitle = null
        )
    }
    annotation.setTitle(title)
    UIKitView(
        modifier = modifier,
        factory = {
            MKMapView().apply {
                addAnnotation(annotation)
                setMapType(mapType.toAppleMapType())
            }
        },
        update = {
            it.addAnnotations(listOf(MKPointAnnotation(location)))
            it.setRegion(
                MKCoordinateRegionMakeWithDistance(
                    centerCoordinate = location,
                    10_000.0 / zoomLevel,
                    10_000.0 / zoomLevel
                ),
                animated = false
            )
        }
    )
}

fun MapType.toAppleMapType() = when (this) {
    MapType.Normal -> MKMapTypeStandard
    MapType.Satellite -> MKMapTypeSatellite
}