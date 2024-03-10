package org.jetbrains.compose.resources

import androidx.compose.runtime.Composable

// TODO: migrate to new resource apis and then remove
@Composable
@OptIn(ExperimentalResourceApi::class)
fun painterResource(resource: String) =
    painterResource(DrawableResource(resource))
