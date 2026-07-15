package dev.shreyash.wonderouscompose.models

import org.jetbrains.compose.resources.StringResource
import wonderous_compose.shared.generated.resources.Res
import wonderous_compose.shared.generated.resources.timelineEvent1001ce
import wonderous_compose.shared.generated.resources.timelineEvent1077ce
import wonderous_compose.shared.generated.resources.timelineEvent1117ce
import wonderous_compose.shared.generated.resources.timelineEvent1199ce
import wonderous_compose.shared.generated.resources.timelineEvent1227ce
import wonderous_compose.shared.generated.resources.timelineEvent1337ce
import wonderous_compose.shared.generated.resources.timelineEvent1347ce
import wonderous_compose.shared.generated.resources.timelineEvent1428ce
import wonderous_compose.shared.generated.resources.timelineEvent1439ce
import wonderous_compose.shared.generated.resources.timelineEvent1492ce
import wonderous_compose.shared.generated.resources.timelineEvent1760ce
import wonderous_compose.shared.generated.resources.timelineEvent1763ce
import wonderous_compose.shared.generated.resources.timelineEvent1783ce
import wonderous_compose.shared.generated.resources.timelineEvent1789ce
import wonderous_compose.shared.generated.resources.timelineEvent1800bce
import wonderous_compose.shared.generated.resources.timelineEvent1914ce
import wonderous_compose.shared.generated.resources.timelineEvent1929ce
import wonderous_compose.shared.generated.resources.timelineEvent1939ce
import wonderous_compose.shared.generated.resources.timelineEvent1957ce
import wonderous_compose.shared.generated.resources.timelineEvent1969ce
import wonderous_compose.shared.generated.resources.timelineEvent2000bce
import wonderous_compose.shared.generated.resources.timelineEvent200bce
import wonderous_compose.shared.generated.resources.timelineEvent2200bce
import wonderous_compose.shared.generated.resources.timelineEvent2500bce
import wonderous_compose.shared.generated.resources.timelineEvent2560bce
import wonderous_compose.shared.generated.resources.timelineEvent2600bce
import wonderous_compose.shared.generated.resources.timelineEvent2700bce
import wonderous_compose.shared.generated.resources.timelineEvent2900bce
import wonderous_compose.shared.generated.resources.timelineEvent322bce
import wonderous_compose.shared.generated.resources.timelineEvent427bce
import wonderous_compose.shared.generated.resources.timelineEvent43ce
import wonderous_compose.shared.generated.resources.timelineEvent447bce
import wonderous_compose.shared.generated.resources.timelineEvent44bce
import wonderous_compose.shared.generated.resources.timelineEvent455ce
import wonderous_compose.shared.generated.resources.timelineEvent4bce
import wonderous_compose.shared.generated.resources.timelineEvent500ce
import wonderous_compose.shared.generated.resources.timelineEvent632ce
import wonderous_compose.shared.generated.resources.timelineEvent753bce
import wonderous_compose.shared.generated.resources.timelineEvent776bce
import wonderous_compose.shared.generated.resources.timelineEvent793ce
import wonderous_compose.shared.generated.resources.timelineEvent79ce
import wonderous_compose.shared.generated.resources.timelineEvent800ce
import wonderous_compose.shared.generated.resources.timelineEvent890bce
import wonderous_compose.shared.generated.resources.timelineLabelConstruction

data class TimelineEvent(
    val year: Int,
    val description: StringResource,
    /** Only used by *start year* event */
    val startYearEventWonderTitle: StringResource? = null,
)


val GlobalEvents = listOf(
    TimelineEvent(-2900, Res.string.timelineEvent2900bce),
    TimelineEvent(-2700, Res.string.timelineEvent2700bce),
    TimelineEvent(-2600, Res.string.timelineEvent2600bce),
    TimelineEvent(-2560, Res.string.timelineEvent2560bce),
    TimelineEvent(-2500, Res.string.timelineEvent2500bce),
    TimelineEvent(-2200, Res.string.timelineEvent2200bce),
    TimelineEvent(-2000, Res.string.timelineEvent2000bce),
    TimelineEvent(-1800, Res.string.timelineEvent1800bce),
    TimelineEvent(-890, Res.string.timelineEvent890bce),
    TimelineEvent(-776, Res.string.timelineEvent776bce),
    TimelineEvent(-753, Res.string.timelineEvent753bce),
    TimelineEvent(-447, Res.string.timelineEvent447bce),
    TimelineEvent(-427, Res.string.timelineEvent427bce),
    TimelineEvent(-322, Res.string.timelineEvent322bce),
    TimelineEvent(-200, Res.string.timelineEvent200bce),
    TimelineEvent(-44, Res.string.timelineEvent44bce),
    TimelineEvent(-4, Res.string.timelineEvent4bce),
    TimelineEvent(43, Res.string.timelineEvent43ce),
    TimelineEvent(79, Res.string.timelineEvent79ce),
    TimelineEvent(455, Res.string.timelineEvent455ce),
    TimelineEvent(500, Res.string.timelineEvent500ce),
    TimelineEvent(632, Res.string.timelineEvent632ce),
    TimelineEvent(793, Res.string.timelineEvent793ce),
    TimelineEvent(800, Res.string.timelineEvent800ce),
    TimelineEvent(1001, Res.string.timelineEvent1001ce),
    TimelineEvent(1077, Res.string.timelineEvent1077ce),
    TimelineEvent(1117, Res.string.timelineEvent1117ce),
    TimelineEvent(1199, Res.string.timelineEvent1199ce),
    TimelineEvent(1227, Res.string.timelineEvent1227ce),
    TimelineEvent(1337, Res.string.timelineEvent1337ce),
    TimelineEvent(1347, Res.string.timelineEvent1347ce),
    TimelineEvent(1428, Res.string.timelineEvent1428ce),
    TimelineEvent(1439, Res.string.timelineEvent1439ce),
    TimelineEvent(1492, Res.string.timelineEvent1492ce),
    TimelineEvent(1760, Res.string.timelineEvent1760ce),
    TimelineEvent(1763, Res.string.timelineEvent1763ce),
    TimelineEvent(1783, Res.string.timelineEvent1783ce),
    TimelineEvent(1789, Res.string.timelineEvent1789ce),
    TimelineEvent(1914, Res.string.timelineEvent1914ce),
    TimelineEvent(1929, Res.string.timelineEvent1929ce),
    TimelineEvent(1939, Res.string.timelineEvent1939ce),
    TimelineEvent(1957, Res.string.timelineEvent1957ce),
    TimelineEvent(1969, Res.string.timelineEvent1969ce)
)

val AllTimelineEvents = buildList {
    addAll(GlobalEvents)
    Wonders.forEach { wonder ->
        add(
            TimelineEvent(
                wonder.startYr,
                Res.string.timelineLabelConstruction,
                startYearEventWonderTitle = wonder.title
            )
        )
    }
}




