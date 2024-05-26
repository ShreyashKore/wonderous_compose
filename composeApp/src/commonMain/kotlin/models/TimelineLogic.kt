package models

val AllTimelineEvents = buildList {
    addAll(GlobalEvents)
    Wonders.forEach { wonder ->
        add(TimelineEvent(wonder.startYr, wonderStartDescription(wonder.title)))
    }
}

fun wonderStartDescription(title: String) = "Construction of $title begins."