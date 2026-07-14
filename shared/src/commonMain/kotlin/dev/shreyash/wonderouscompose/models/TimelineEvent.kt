package models

import org.jetbrains.compose.resources.StringResource
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.timelineEvent1001ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1077ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1117ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1199ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1227ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1337ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1347ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1428ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1439ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1492ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1760ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1763ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1783ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1789ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1800bce
import wonderouscompose.composeapp.generated.resources.timelineEvent1914ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1929ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1939ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1957ce
import wonderouscompose.composeapp.generated.resources.timelineEvent1969ce
import wonderouscompose.composeapp.generated.resources.timelineEvent2000bce
import wonderouscompose.composeapp.generated.resources.timelineEvent200bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2200bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2500bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2560bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2600bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2700bce
import wonderouscompose.composeapp.generated.resources.timelineEvent2900bce
import wonderouscompose.composeapp.generated.resources.timelineEvent322bce
import wonderouscompose.composeapp.generated.resources.timelineEvent427bce
import wonderouscompose.composeapp.generated.resources.timelineEvent43ce
import wonderouscompose.composeapp.generated.resources.timelineEvent447bce
import wonderouscompose.composeapp.generated.resources.timelineEvent44bce
import wonderouscompose.composeapp.generated.resources.timelineEvent455ce
import wonderouscompose.composeapp.generated.resources.timelineEvent4bce
import wonderouscompose.composeapp.generated.resources.timelineEvent500ce
import wonderouscompose.composeapp.generated.resources.timelineEvent632ce
import wonderouscompose.composeapp.generated.resources.timelineEvent753bce
import wonderouscompose.composeapp.generated.resources.timelineEvent776bce
import wonderouscompose.composeapp.generated.resources.timelineEvent793ce
import wonderouscompose.composeapp.generated.resources.timelineEvent79ce
import wonderouscompose.composeapp.generated.resources.timelineEvent800ce
import wonderouscompose.composeapp.generated.resources.timelineEvent890bce
import wonderouscompose.composeapp.generated.resources.timelineLabelConstruction

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




