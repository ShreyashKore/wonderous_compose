package ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DensityQualifier
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.painterResource

/**
 * Used mostly for displaying Wonder specific images from the `files/images/{wonder}/` folder.
 *
 * To keep project more organized; Wonder specific images are not kept in flat `drawable` folders.
 * Instead they are kept in wonder specific `files/images/{wonder}/` folders.
 * The higher resolution versions are stored in `hdpi` and `xxhdpi` folders.
 *
 */
@Composable
@OptIn(InternalResourceApi::class)
fun filePainterResource(resource: String): Painter {
    val parentFolder = resource.substringBeforeLast("/")
    val resourceFile = resource.substringAfterLast("/")
    return painterResource(
        DrawableResource(
            id = resource,
            items = setOf(
                ResourceItem(
                    setOf(DensityQualifier.HDPI),
                    "composeResources/wonderouscompose.composeapp.generated.resources/$parentFolder/hdpi/$resourceFile",
                    -1,
                    -1
                ),
                ResourceItem(
                    setOf(DensityQualifier.XXHDPI),
                    "composeResources/wonderouscompose.composeapp.generated.resources/$parentFolder/xxhdpi/$resourceFile",
                    -1,
                    -1
                ),
                ResourceItem(
                    setOf(),
                    "composeResources/wonderouscompose.composeapp.generated.resources/$resource",
                    -1,
                    -1
                ),
            )
        )
    )
}
