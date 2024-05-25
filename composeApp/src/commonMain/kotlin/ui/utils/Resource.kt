package org.jetbrains.compose.resources

import androidx.compose.runtime.Composable

// TODO: migrate to new resource apis and then remove
@Composable
@OptIn(InternalResourceApi::class)
fun painterResource(resource: String) =
    painterResource(
        DrawableResource(
            resource,
            items = setOf(
                ResourceItem(
                    setOf(),
                    "composeResources/wonderouscompose.composeapp.generated.resources/$resource",
                    -1, -1
                )
            )
        )
    )
