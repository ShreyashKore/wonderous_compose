package models

data class TimelineEvent(val year: Int, val description: String)


val GlobalEvents = listOf(
    TimelineEvent(-2900, "First known use of papyrus by Egyptians"),
    TimelineEvent(-2700, "The Old Kingdom begins in Egypt"),
    TimelineEvent(-2600, "Emergence of Mayan culture in the Yucatán Peninsula"),
    TimelineEvent(-2560, "King Khufu completes the Great Pyramid of Giza"),
    TimelineEvent(-2500, "The mammoth goes extinct"),
    TimelineEvent(-2200, "Completion of Stonehenge"),
    TimelineEvent(-2000, "Domestication of the horse"),
    TimelineEvent(-1800, "Alphabetic writing emerges"),
    TimelineEvent(-890, "Homer writes the Iliad and the Odyssey"),
    TimelineEvent(-776, "First recorded Ancient Olympic Games"),
    TimelineEvent(-753, "Founding of Rome"),
    TimelineEvent(-447, "Building of the Parthenon at Athens started"),
    TimelineEvent(-427, "Birth of Greek Philosopher Plato"),
    TimelineEvent(-322, "Death of Aristotle (61), the first genuine scientist"),
    TimelineEvent(-200, "Paper is invented in the Han Dynasty"),
    TimelineEvent(-44, "Death of Julius Caesar; beginning of the Roman Empire"),
    TimelineEvent(-4, "Birth of Jesus Christ"),
    TimelineEvent(43, "The Roman Empire enters Great Britain for the first time"),
    TimelineEvent(79, "Destruction of Pompeii by the volcano Vesuvius"),
    TimelineEvent(455, "End of the Roman Empire"),
    TimelineEvent(500, "Tikal becomes the first great Maya city"),
    TimelineEvent(632, "Death of Muhammad (61), founder of Islam"),
    TimelineEvent(793, "The Vikings first invade Britain"),
    TimelineEvent(800, "Gunpowder is invented in China"),
    TimelineEvent(1001, "Leif Erikson settles during the winter in present-day eastern Canada"),
    TimelineEvent(1077, "The Construction of the Tower of London begins"),
    TimelineEvent(1117, "The University of Oxford is founded"),
    TimelineEvent(1199, "Europeans first use compasses"),
    TimelineEvent(1227, "Death of Genghis Khan (65)"),
    TimelineEvent(
        1337,
        "The Hundred Years' War begins as England and France struggle for dominance."
    ),
    TimelineEvent(
        1347,
        "The first of many concurrences of the Black Death plague, believed to have wiped out as many as 50% of Europe's population by its end"
    ),
    TimelineEvent(1428, "Birth of the Aztec Empire in Mexico"),
    TimelineEvent(1439, "Johannes Gutenberg invents the printing press"),
    TimelineEvent(1492, "Christopher Columbus reaches the New World"),
    TimelineEvent(1760, "The industrial revolution begins"),
    TimelineEvent(1763, "Development of the Watt steam engine"),
    TimelineEvent(1783, "End of the American War of Independence from the British Empire"),
    TimelineEvent(1789, "The French Revolution begins"),
    TimelineEvent(1914, "World War I"),
    TimelineEvent(1929, "Black Tuesday signals the beginning of the Great Depression"),
    TimelineEvent(1939, "World War II"),
    TimelineEvent(1957, "Launch of Sputnik 1 by the Soviet Union"),
    TimelineEvent(1969, "Apollo 11 mission lands on the moon")
)

