package ui.composables

import androidx.compose.foundation.lazy.LazyListState

val LazyListState.firstItemScrollProgress: Float
    get() =
        if (firstVisibleItemIndex == 0)
            firstVisibleItemScrollOffset.toFloat() / (layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat()
                ?.coerceAtLeast(1f)
                ?: 1f)
        else 1f

@OptIn(ExperimentalStdlibApi::class)
fun LazyListState.scrollProgressFor(i: Int): Float = when (firstVisibleItemIndex) {
    in 0..<i -> 0f
    i -> firstVisibleItemScrollOffset.toFloat() / (layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat()
        ?.coerceAtLeast(1f) ?: 1f)

    else -> 1f
}

