package com.hamama.kwhi

import androidx.compose.runtime.staticCompositionLocalOf
import org.w3c.dom.Element

val LocalLayerContainer = staticCompositionLocalOf<Element> {
    error("CompositionLocal LayerContainer not provided")
    // you can replace this with document.body!!
}


