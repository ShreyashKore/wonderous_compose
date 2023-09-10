package models


sealed class Wonder(
    val title: String,
    val subTitle: String,
    val regionTitle: String,
    val historyInfo1: String,
    val historyInfo2: String,
    val constructionInfo1: String,
    val constructionInfo2: String,
    val locationInfo1: String,
    val locationInfo2: String,
    val pullQuote1Top: String,
    val pullQuote1Bottom: String,
    val pullQuote1Author: String,
    val pullQuote2: String = "",
    val pullQuote2Author: String = "",
    val callout1: String = "",
    val callout2: String = "",
    val unsplashCollectionId: String,
    val videoId: String,
    val videoCaption: String = "",
    val mapCaption: String = "",
    val imageIds: List<String> = emptyList(),
    val facts: List<String> = emptyList(),
    val startYr: Int = 0,
    val endYr: Int = 0,
    val artifactStartYr: Int = 0,
    val artifactEndYr: Int = 0,
    val artifactCulture: String = "",
    val artifactGeolocation: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val highlightArtifacts: List<String> = emptyList(), // IDs used to assemble HighlightsData: String, should not be used directly
    val hiddenArtifacts: List<String> = emptyList(), // IDs used to assemble CollectibleData: String, should not be used directly
    val events: Map<Int, String>,
    val searchData: List<SearchData> = emptyList(),
    val searchSuggestions: List<String> = emptyList(),
) {
    val gps get() = GpsPosition(lat, lng)
    val titleWithBreaks: String get() = title.replaceFirst(' ', '\n')
}

val Wonders = listOf(
    ChichenItza, ChristRedeemer
)

data object ChichenItza : Wonder(
    title = "Chichen Itza",
    subTitle = "The Great Mayan City",
    regionTitle = "Yucatan, Mexico",
    startYr = 550,
    endYr = 1550,
    historyInfo1 = "Chichen Itza was a powerful regional capital controlling north and central Yucatán. The earliest hieroglyphic date discovered at Chichen Itza is equivalent to 832 CE, while the last known date was recorded in the Osario temple in 998 CE.\nDominating the North Platform of Chichen Itza is the famous Temple of Kukulcán. The temple was identified by the first Spaniards to see it, as El Castillo (\"the castle\"), and it regularly is referred to as such. The temple was identified by the first Spaniards to see it, as El Castillo (\"the castle\"), and it regularly is referred to as such.",
    historyInfo2 = "The city was thought to have the most diverse population in the Maya world, a factor that could have contributed to this architectural variety.",
    constructionInfo1 = "The structures of Chichen Itza were built from precisely chiseled limestone blocks that fit together perfectly without the mortar. Many of these stone buildings were originally painted in red, green, blue and purple colors depending on the availability of the pigments.\nThe stepped pyramid El Castillo stands about 98 feet (30 m) high and consists of a series of nine square terraces, each approximately 8.4 feet (2.57 m) high, with a 20 foot (6 m) high temple upon the summit.",
    constructionInfo2 = "It was built upon broken terrain, which was artificially leveled to support structures such as the Castillo pyramid. Important buildings within the center were connected by a dense network of paved roads called sacbeob.",
    locationInfo1 = "Chichen Itza is located in the eastern portion of Yucatán state in Mexico. Nearby, four large sinkholes, called cenotes, could have provided plentiful water year round at Chichen, making it attractive for settlement.",
    locationInfo2 = "Of these cenotes, the \"Cenote Sagrado\" or Sacred Cenote, was used for the sacrifice of precious objects and human beings as a form of worship to the Maya rain god Chaac.",
    pullQuote1Top = "The Beauty Between",
    pullQuote1Bottom = "the Heavens and the Underworld",
    pullQuote1Author = "",
    pullQuote2 = "The Maya and Toltec vision of the world and the universe is revealed in their stone monuments and artistic works.",
    pullQuote2Author = "UNESCO",
    callout1 = "The site exhibits a multitude of architectural styles, reminiscent of styles seen in central Mexico and of the Puuc and Chenes styles of the Northern Maya lowlands.",
    callout2 = "The city comprised an area of at least 1.9 sq miles (5 sq km) of densely clustered architecture.",
    videoId = "Q6eBJjdca14",
    videoCaption = "“Ancient Maya 101 | National Geographic.” Youtube, uploaded by National Geographic.",
    events = mapOf(),
    unsplashCollectionId = "SUK0tuMnLLw"
)

data object ChristRedeemer : Wonder(
    title = "Christ the Redeemer",
    subTitle = "A symbol of peace",
    regionTitle = "Rio de Janeiro, Brazil",
    historyInfo1 = "The placement of a Christian monument on Mount Corcovado was first suggested in the mid-1850s to honor Princess Isabel, regent of Brazil and the daughter of Emperor Pedro II, but the project was not approved.\nIn 1889 the country became a republic, and owing to the separation of church and state the proposed statue was dismissed.",
    historyInfo2 = "The Catholic Circle of Rio made a second proposal for a landmark statue on the mountain in 1920. The group organized an event called Semana do Monumento (\"Monument Week\") to attract donations and collect signatures to support the building of the statue. The organization was motivated by what they perceived as \"Godlessness\" in the society.\nThe designs considered for the \"Statue of the Christ\" included a representation of the Christian cross, a statue of Jesus with a globe in his hands, and a pedestal symbolizing the world.",
    constructionInfo1 = "Artist Carlos Oswald and local engineer Heitor da Silva Costa designed the statue. French sculptor Paul Landowski created the work. In 1922, Landowski commissioned fellow Parisian Romanian sculptor Gheorghe Leonida, who studied sculpture at the Fine Arts Conservatory in Bucharest and in Italy.",
    constructionInfo2 = "A group of engineers and technicians studied Landowski\'s submissions and felt building the structure of reinforced concrete instead of steel was more suitable for the cross-shaped statue. The concrete making up the base was supplied from Limhamn, Sweden. The outer layers are soapstone, chosen for its enduring qualities and ease of use.",
    locationInfo1 = "Corcovado, which means \"hunchback\" in Portuguese, is a mountain in central Rio de Janeiro, Brazil. It is a 2,329 foot (710 m) granite peak located in the Tijuca Forest, a national park.",
    locationInfo2 = "Corcovado hill lies just west of the city center but is wholly within the city limits and visible from great distances.",
    pullQuote1Top = "'A Perfect Union Between",
    pullQuote1Bottom = "Nature and Architecture",
    pullQuote1Author = "",
    videoId = "k_615AauSds",
    events = mapOf(),
    unsplashCollectionId = "dPgX5iK8Ufo"
)