package example.map

import kotlinx.datetime.Clock

fun timeMs(): Long = Clock.System.now().toEpochMilliseconds()
