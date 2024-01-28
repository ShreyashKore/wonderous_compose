package ui.composables

import androidx.compose.foundation.lazy.LazyListState

val LazyListState.firstItemScrollProgress: Float
    get() = if (firstVisibleItemIndex == 0) firstVisibleItemScrollOffset.toFloat() / (layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat()
        ?.coerceAtLeast(1f) ?: 1f)
    else 1f

fun LazyListState.scrollProgressFor(i: Int): Float = when (firstVisibleItemIndex) {
    in 0..i -> {
        val item = layoutInfo.visibleItemsInfo.find { it.index == i }
        if (item == null) 0f else
            1 - (item.offset.toFloat() + item.size) / (layoutInfo.viewportSize.height + item.size)
    }

    else -> 1f
}