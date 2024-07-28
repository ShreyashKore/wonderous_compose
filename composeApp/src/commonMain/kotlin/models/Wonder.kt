package models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import data.search_data.ChichenSearchData
import data.search_data.ChichenSearchSuggestions
import data.search_data.ChristRedeemerSearchData
import data.search_data.ChristRedeemerSearchSuggestions
import data.search_data.ColosseumSearchData
import data.search_data.ColosseumSearchSuggestions
import data.search_data.GreatWallSearchData
import data.search_data.GreatWallSearchSuggestions
import data.search_data.MachuPicchuSearchData
import data.search_data.MachuPicchuSearchSuggestions
import data.search_data.PetraSearchData
import data.search_data.PetraSearchSuggestions
import data.search_data.PyramidsGizaSearchData
import data.search_data.PyramidsGizaSearchSuggestions
import data.search_data.TajMahalSearchData
import data.search_data.TajMahalSearchSuggestions
import localization.LocalStrings


sealed class Wonder {
    @get:Composable
    abstract val title: String
    abstract val subTitle: String
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

    @get:Composable
    abstract val events: Map<Int, String>
    val searchData: List<SearchData> = emptyList(),
    val searchSuggestions: List<String> = emptyList(),
    val latLng get() = LatLng(lat, lng)
    val titleWithBreaks: String get() = title.replaceFirst(' ', '\n')

    companion object
}

val Wonders = listOf(
    GreatWall, Petra, Colosseum, ChichenItza, MachuPicchu,
    TajMahal, ChristRedeemer, PyramidsGiza,
)

@Composable
fun Wonder.Companion.parse(name: String?): Wonder {
    if (name == null) return ChichenItza
    return Wonders.firstOrNull {
        it.title.lowercase() == name.lowercase()
    } ?: GreatWall
}

data object ChichenItza : Wonder() {

    override val title @Composable get() = LocalStrings.chichenItzaTitle
    override val subTitle get() = "The Great Mayan City"
    override val regionTitle get() = "Yucatan, Mexico"
    override val startYr get() = 550
    override val endYr get() = 1550
    artifactStartYr = 500,
    artifactEndYr = 1600,
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
    lat = 20.68346184201756,
    lng = -88.56769676930931,

    override val events: Map<Int, String> @Composable get() = mapOf(
        600 to LocalStrings.chichenItzaTitle,
        832 to "The earliest hieroglyphic date discovered at Chichen Itza",
        998 to "Last known date recorded in the Osario temple",
        1100 to "Chichen Itza declines as a regional center",
        1527 to "Invaded by Spanish Conquistador Francisco de Montejo",
        1535 to "All Spanish are driven from the Yucatán Peninsula",
    )
    searchData = ChichenSearchData,
    searchSuggestions = ChichenSearchSuggestions,
    unsplashCollectionId = "SUK0tuMnLLw"

}

data object ChristRedeemer : Wonder(
    title = "Christ the Redeemer",
    subTitle = "A symbol of peace",
    regionTitle = "Rio de Janeiro, Brazil",
    artifactGeolocation = "Brazil",
    pullQuote1Top = "A Perfect Union Between",
    pullQuote1Bottom = "Nature and Architecture",
    pullQuote2 = "The statue looms large on the landscape, but it hides as much as it reveals about the diverse religious life of Brazilians.",
    pullQuote2Author = "Thomas Tweed",
    callout1 = "The statue of Christ the Redeemer with open arms, a symbol of peace, was chosen.",
    callout2 = "Construction took nine years, from 1922 to 1931, and cost the equivalent of US$250,000 (equivalent to $3,600,000 in 2020) and the monument opened on October 12, 1931.",
    videoCaption = "“The Majestic Statue of Christ the Redeemer - Seven Wonders of the Modern World - See U in History.” Youtube, uploaded by See U in History / Mythology.",
    mapCaption = "Map showing location of Christ the Redeemer in Rio de Janeiro, Brazil.",
    historyInfo1 = "The placement of a Christian monument on Mount Corcovado was first suggested in the mid-1850s to honor Princess Isabel, regent of Brazil and the daughter of Emperor Pedro II, but the project was not approved.\nIn 1889 the country became a republic, and owing to the separation of church and state the proposed statue was dismissed.",
    historyInfo2 = "The Catholic Circle of Rio made a second proposal for a landmark statue on the mountain in 1920. The group organized an event called Semana do Monumento (\"Monument Week\") to attract donations and collect signatures to support the building of the statue. The organization was motivated by what they perceived as \"Godlessness\" in the society.\nThe designs considered for the \"Statue of the Christ\" included a representation of the Christian cross, a statue of Jesus with a globe in his hands, and a pedestal symbolizing the world.",
    constructionInfo1 = "Artist Carlos Oswald and local engineer Heitor da Silva Costa designed the statue. French sculptor Paul Landowski created the work. In 1922, Landowski commissioned fellow Parisian Romanian sculptor Gheorghe Leonida, who studied sculpture at the Fine Arts Conservatory in Bucharest and in Italy.",
    constructionInfo2 = "A group of engineers and technicians studied Landowski's submissions and felt building the structure of reinforced concrete instead of steel was more suitable for the cross-shaped statue. The concrete making up the base was supplied from Limhamn, Sweden. The outer layers are soapstone, chosen for its enduring qualities and ease of use.",
    locationInfo1 = "Corcovado, which means \"hunchback\" in Portuguese, is a mountain in central Rio de Janeiro, Brazil. It is a 2,329 foot (710 m) granite peak located in the Tijuca Forest, a national park.",
    locationInfo2 = "Corcovado hill lies just west of the city center but is wholly within the city limits and visible from great distances.",
    startYr = 1922,
    endYr = 1931,
    artifactStartYr = 1600,
    artifactEndYr = 2100,
    lat = -22.95238891944396,
    lng = -43.21045520611561,
    pullQuote1Author = "",
    videoId = "k_615AauSds",
    events = mapOf(
        1850 to "Plans for the statue were first proposed by Pedro Maria Boss upon Mount Corcovado. This was never approved, however.",
        1921 to "A new plan was proposed by the Roman Catholic archdiocese, and after the citizens of Rio de Janeiro petitioned the president, it was finally approved.",
        1922 to "The foundation of the statue was ceremoniously laid out to commemorate Brazil’s independence from Portugal.",
        1926 to "Construction officially began after the initial design was chosen via a competition and amended by Brazilian artists and engineers.",
        1931 to "Construction of the statue was completed, standing 98’ tall with a 92’ wide arm span.",
        2006 to "A chapel was consecrated at the statue’s base to Our Lady of Aparecida to mark the statue’s 75th anniversary.",
    ),
    searchData = ChristRedeemerSearchData,
    searchSuggestions = ChristRedeemerSearchSuggestions,
    unsplashCollectionId = "dPgX5iK8Ufo"
)

data object Petra : Wonder(
    title = "Petra",
    subTitle = "The Lost City",
    regionTitle = "Ma’an, Jordan",
    artifactCulture = "Nabataean",
    artifactGeolocation = "Levant",
    pullQuote1Top = "A Rose-Red City",
    pullQuote1Bottom = "Half as Old as Time",
    pullQuote1Author = "John William Burgon",
    pullQuote2 = "Petra is a brilliant display of man’s artistry in turning barren rock into a majestic wonder.",
    pullQuote2Author = "Edward Dawson",
    callout1 = "They were particularly skillful in harvesting rainwater, agriculture and stone carving.",
    callout2 = "Perhaps a more prominent resemblance to Hellenistic style in Petra comes with its Treasury.",
    videoCaption = "“Stunning Stone Monuments of Petra | National Geographic.” Youtube, uploaded by National Geographic.",
    mapCaption = "Map showing location of Petra in Ma’an Governorate, Jordan.",
    historyInfo1 = "The area around Petra has been inhabited from as early as 7000 BCE, and the Nabataeans might have settled in what would become the capital city of their kingdom as early as the 4th century BCE.\nThe trading business gained the Nabataeans considerable revenue and Petra became the focus of their wealth. The Nabataeans were accustomed to living in the barren deserts, unlike their enemies, and were able to repel attacks by taking advantage of the area's mountainous terrain.",
    historyInfo2 = "Petra flourished in the 1st century CE, when its famous Al-Khazneh structure - believed to be the mausoleum of Nabataean king Aretas IV - was constructed, and its population peaked at an estimated 20,000 inhabitants.\nAccess to the city is through a 3/4 mile-long (1.2 km) gorge called the Siq, which leads directly to the Khazneh.",
    constructionInfo1 = "Famous for its rock-cut architecture and water conduit system, Petra is also called the 'Red Rose City' because of the color of the stone from which it is carved.\nAnother thing Petra is known for is its Hellenistic ('Greek') architecture. These influences can be seen in many of the facades at Petra and are a reflection of the cultures that the Nabataeans traded with.",
    constructionInfo2 = "The facade of the Treasury features a broken pediment with a central tholos ('dome') inside, and two obelisks appear to form into the rock of Petra at the top. Near the bottom of the Treasury, we see twin Greek Gods: Pollux, Castor, and Dioscuri, who protect travelers on their journeys. \nNear the top of the Treasury, two victories are seen standing on each side of a female figure on the tholos. This female figure is believed to be the Isis-Tyche, Isis being the Egyptian Goddess and Tyche being the Greek Goddess of good fortune.",
    locationInfo1 = "Petra is located in southern Jordan. It is adjacent to the mountain of Jabal Al-Madbah, in a basin surrounded by mountains forming the eastern flank of the Arabah valley running from the Dead Sea to the Gulf of Aqaba.",
    locationInfo2 = "The area around Petra has been inhabited from as early as 7000 BC, and the Nabataeans might have settled in what would become the capital city of their kingdom as early as the 4th century BC.\nArchaeological work has only discovered evidence of Nabataean presence dating back to the second century BC, by which time Petra had become their capital. The Nabataeans were nomadic Arabs who invested in Petra's proximity to the incense trade routes by establishing it as a major regional trading hub.",
    startYr = -312,
    endYr = 100,
    artifactStartYr = -500,
    artifactEndYr = 500,
    lat = 30.328830750209903,
    lng = 35.44398203484667,
    videoId = "ezDiSkOU0wc",
    events = mapOf(
        -1200 to "First Edomites occupied the area and established a foothold.",
        -106 to "Became part of the Roman province Arabia",
        551 to "After being damaged by earthquakes, habitation of the city all but ceased.",
        1812 to "Rediscovered by the Swiss traveler Johann Ludwig Burckhardt.",
        1958 to "Excavations led on the site by the British School of Archaeology and the American Center of Oriental Research.",
        1989 to "Appeared in the film Indiana Jones and The Last Crusade.",
    ),
    searchData = PetraSearchData,
    searchSuggestions = PetraSearchSuggestions,
    unsplashCollectionId = "qWQJbDvCMW8",
)

data object Colosseum : Wonder(
    title = "Colosseum",
    subTitle = "The icon of Rome",
    regionTitle = "Rome, Italy",
    artifactCulture = "Roman",
    artifactGeolocation = "Roman Empire",
    pullQuote1Top = "Still the Largest Standing",
    pullQuote1Bottom = "Amphitheater in the World Today",
    pullQuote2 = "When falls the Coliseum, Rome shall fall; And when Rome falls - the World.",
    pullQuote2Author = "Lord Byron",
    callout1 = "It was used for gladiatorial contests and public spectacles including animal hunts, executions, reenactments of famous battles, and dramas based on Roman mythology, and mock sea battles.",
    callout2 = "It is the largest ancient amphitheater ever built, and is still the largest standing amphitheater in the world today, despite its age.",
    videoCaption = "“Ancient Rome 101 | National Geographic.” Youtube, uploaded by National Geographic.",
    mapCaption = "Map showing location of Colosseum in Rome, Italy.",
    historyInfo1 = "The Colosseum is an oval amphitheater in the center of the city of Rome, Italy. Unlike Roman theaters that were built into hillsides, the Colosseum is an entirely free-standing structure.",
    historyInfo2 = "The building ceased to be used for entertainment in the early medieval era. By the late 6th century a small chapel had been built into the structure of the amphitheater, and the arena was converted into a cemetery. \nThe numerous vaulted spaces in the arcades under the seating were converted into housing and workshops, and are recorded as still being rented out as late as the 12th century.",
    constructionInfo1 = "Construction began under the emperor Vespasian (r. 69-79 CE) in 72 and was completed in 80 CE under his successor and heir, Titus (r. 79-81). Further modifications were made during the reign of Domitian (r. 81-96).\nThe Colosseum is built of travertine limestone, tuff (volcanic rock), and brick-faced concrete. The outer wall is estimated to have required over 3.5 million cubic feet of travertine stone which were set without mortar; they were held together by 300 tons of iron clamps.",
    constructionInfo2 = "It could hold an estimated 50,000 to 80,000 spectators at various points in its history, having an average audience of some 65,000.",
    locationInfo1 = "Following the Great Fire of Rome in 64 CE, Emperor Nero seized much of the destroyed area to build his grandiose Domus Aurea (\"Golden House\"). A severe embarrassment to Nero's successors, parts of this extravagant palace and grounds, encompassing 1 sq mile, were filled with earth and built over.",
    locationInfo2 = "On the site of the lake, in the middle of the palace grounds, Emperor Vespasian would build the Colosseum as part of a Roman resurgence.",
    startYr = 70,
    endYr = 80,
    artifactStartYr = 0,
    artifactEndYr = 500,
    lat = 41.890242126393495,
    lng = 12.492349361871392,
    pullQuote1Author = "",
    videoId = "GXoEpNjgKzg",
    events = mapOf(
        70 to "Colosseum construction was started during the Vespasian reign over what used to be a private lake for the previous four emperors. This was done in an attempt to revitalize Rome from their tyrannical reign.",
        82 to "The uppermost floor was built, and the structure was officially completed by Domitian.",
        1140 to "The arena was repurposed as a fortress for the Frangipane and Annibaldi families. It was also at one point used as a church.",
        1490 to "Pope Alexander VI permitted the site to be used as a quarry, for both storing and salvaging building materials.",
        1829 to "Preservation of the Colosseum officially began, after more than a millennia of dilapidation and vandalism. Pope Pius VIII was notably devoted to this project.",
        1990 to "A restoration project was undertaken to ensure the Colosseum remained a major tourist attraction for Rome. It currently stands as one of the greatest sources of tourism revenue in Italy.",
    ),
    searchData = ColosseumSearchData,
    searchSuggestions = ColosseumSearchSuggestions,
    unsplashCollectionId = "VPdti8Kjq9o",
)

data object PyramidsGiza : Wonder(
    title = "Pyramids of Giza",
    subTitle = "The ancient wonder",
    regionTitle = "Cairo, Egypt",
    artifactCulture = "Egyptian",
    artifactGeolocation = "Egypt",
    pullQuote1Top = "The Tallest Structures on Earth",
    pullQuote1Bottom = "Until the Advent of Modern Skyscrapers",
    pullQuote2 = "From the heights of these pyramids, forty centuries look down on us.",
    pullQuote2Author = "Napoleon Bonaparte",
    callout1 = "It is theorized the pyramid not only served as a tomb for the pharaoh, but also as a storage pit for various items he would need in the afterlife.",
    callout2 = "The Great Pyramid consists of an estimated 2.3 million blocks. Approximately 5.5 million tonnes of limestone, 8,000 tonnes of granite, and 500,000 tonnes of mortar were used in the construction.",
    videoCaption = "“The Great Pyramids of Giza | Egypt’s Ancient Mysteries | National Geographic UK.” Youtube, uploaded by National Geographic UK.",
    mapCaption = "Map showing location of Giza Pyramids in Greater Cairo, Egypt.",
    historyInfo1 = "The Giza pyramid complex, also called the Giza necropolis, is the site on the Giza Plateau in Greater Cairo, Egypt that includes the Great Pyramid of Giza, the Pyramid of Khafre, and the Pyramid of Menkaure, along with their associated pyramid complexes and the Great Sphinx of Giza. All were built during the Fourth Dynasty of the Old Kingdom of Ancient Egypt, between 2600 and 2500 BCE.",
    historyInfo2 = "The pyramids of Giza and others are thought to have been constructed to house the remains of the deceased pharaohs who ruled over Ancient Egypt. A portion of the pharaoh's spirit called his ka was believed to remain with his corpse. Proper care of the remains was necessary in order for the former Pharaoh to perform his new duties as king of the dead.",
    constructionInfo1 = "Most construction theories are based on the idea that the pyramids were built by moving huge stones from a quarry and dragging and lifting them into place. In building the pyramids, the architects might have developed their techniques over time.\nThey would select a site on a relatively flat area of bedrock — not sand — which provided a stable foundation. After carefully surveying the site and laying down the first level of stones, they constructed the pyramids in horizontal levels, one on top of the other.",
    constructionInfo2 = "For the Great Pyramid, most of the stone for the interior seems to have been quarried immediately to the south of the construction site. The smooth exterior of the pyramid was made of a fine grade of white limestone that was quarried across the Nile.\nTo ensure that the pyramid remained symmetrical, the exterior casing stones all had to be equal in height and width. Workers might have marked all the blocks to indicate the angle of the pyramid wall and trimmed the surfaces carefully so that the blocks fit together. During construction, the outer surface of the stone was smooth limestone; excess stone has eroded as time has passed.",
    locationInfo1 = "The site is at the edges of the Western Desert, approximately 5.6 miles (9 km) west of the Nile River in the city of Giza, and about 8 miles (13 km) southwest of the city center of Cairo.",
    locationInfo2 = "Currently, the pyramids are located in the northwestern side of the Western Desert, and it is considered to be one of the best recognizable and the most visited tourist attractions of the planet.",
    startYr = -2600,
    endYr = -2500,
    artifactStartYr = -2800,
    artifactEndYr = -2300,
    pullQuote1Author = "",
    videoId = "lJKX3Y7Vqvs",
    events = mapOf(
        -2575 to "Construction of the 3 pyramids began for three kings of the 4th dynasty; Khufu, Khafre, and Menkaure.",
        -2465 to "Construction began on the smaller surrounding structures called Mastabas for royalty of the 5th and 6th dynasties.",
        443 to "Greek author Herodotus speculated that the pyramids were built in the span of 20 years with over 100,000 slave laborers. This assumption would last for over 1500 years.",
        1925 to "Tomb of Queen Hetepheres was discovered, containing furniture and jewelry. One of the last remaining treasure-filled tombs after many years of looting and plundering.",
        1979 to "Designated a UNESCO World Heritage Site to prevent any more unauthorized plundering and vandalism.",
        1990 to "Discovery of laborer’s districts suggest that the workers building the pyramids were not slaves, and an ingenious building method proved a relatively small work-force was required to build such immense structures.",
    ),
    searchData = PyramidsGizaSearchData,
    searchSuggestions = PyramidsGizaSearchSuggestions,
    lat = 29.9792,
    lng = 31.1342,
    unsplashCollectionId = "CSEvB5Tza9E",
)

data object MachuPicchu : Wonder(
    title = "Machu Picchu",
    subTitle = "Citadel of the Inca",
    regionTitle = "Cusco Region, Peru",
    artifactCulture = "Inca",
    artifactGeolocation = "South America",
    pullQuote1Top = "Few Romances Can Ever Surpass",
    pullQuote1Bottom = "That of the Granite Citadel",
    pullQuote1Author = "Hiram Bingham",
    pullQuote2 = "In the variety of its charms and the power of its spell, I know of no other place in the world which can compare with it.",
    pullQuote2Author = "Hiram Bingham",
    callout1 = "During its use as a royal estate, it is estimated that about 750 people lived there, with most serving as support staff who lived there permanently.",
    callout2 = "The Incas were masters of this technique, called ashlar, in which blocks of stone are cut to fit together tightly without mortar.",
    videoCaption = "“Machu Picchu 101 | National Geographic.” Youtube, uploaded by National Geographic.",
    mapCaption = "Map showing location of Machu Picchu in the Eastern Cordillera of southern Peru.",
    historyInfo1 = "Machu Picchu is a 15th-century Inca citadel located in the Eastern Cordillera of southern Peru on a 2,430-meter (7,970 ft) mountain ridge. Construction appears to date from two great Inca rulers, Pachacutec Inca Yupanqui (1438–1471 CE) and Túpac Inca Yupanqui (1472–1493 CE).",
    historyInfo2 = "There is a consensus among archeologists that Pachacutec ordered the construction of the royal estate for his use as a retreat, most likely after a successful military campaign.\nRather it was used for 80 years before being abandoned, seemingly because of the Spanish conquests in other parts of the Inca Empire.",
    constructionInfo1 = "The central buildings use the classical Inca architectural style of polished dry-stone walls of regular shape. \nInca walls have many stabilizing features: doors and windows are trapezoidal, narrowing from bottom to top; corners usually are rounded; inside corners often incline slightly into the rooms, and outside corners were often tied together by \"L\"-shaped blocks.",
    constructionInfo2 = "This precision construction method made the structures at Machu Picchu resistant to seismic activity.\nThe site itself may have been intentionally built on fault lines to afford better drainage and a ready supply of fractured stone.",
    locationInfo1 = "Machu Picchu is situated above a bow of the Urubamba River, which surrounds the site on three sides, where cliffs drop vertically for 1,480 feet (450 m) to the river at their base. The location of the city was a military secret, and its deep precipices and steep mountains provided natural defenses.",
    locationInfo2 = "The Inca Bridge, an Inca grass rope bridge, across the Urubamba River in the Pongo de Mainique, provided a secret entrance for the Inca army. Another Inca bridge was built to the west of Machu Picchu, the tree-trunk bridge, at a location where a gap occurs in the cliff that measures 20 feet (6 m).",
    startYr = 1450,
    endYr = 1572,
    artifactStartYr = 1200,
    artifactEndYr = 1700,
    videoId = "cnMa-Sm9H4k",
    lat = -13.162690683637758,
    lng = -72.54500778824891, events = mapOf(
        1438 to "Speculated to be built and occupied by Inca ruler Pachacuti Inca Yupanqui.",
        1572 to "The last Inca rulers used the site as a bastion to rebel against Spanish rule until they were ultimately wiped out.",
        1867 to "Speculated to have been originally discovered by German explorer Augusto Berns, but his findings were never effectively publicized.",
        1911 to "Introduced to the world by Hiram Bingham of Yale University, who was led there by locals after disclosing he was searching for Vilcabamba, the ’lost city of the Incas’.",
        1964 to "Surrounding sites were excavated thoroughly by Gene Savoy, who found a much more suitable candidate for Vilcabamba in the ruin known as Espíritu Pampa.",
        1997 to "Since its rediscovery, growing numbers of tourists have visited the Machu Picchu each year, with numbers exceeding 1.4 million in 2017.",
    ),
    searchData = MachuPicchuSearchData,
    searchSuggestions = MachuPicchuSearchSuggestions,
    unsplashCollectionId = "wUhgZTyUnl8"
)

data object GreatWall : Wonder(
    title = "The Great Wall",
    subTitle = "Longest structure on Earth",
    regionTitle = "China",
    artifactCulture = "Chinese",
    artifactGeolocation = "China",
    pullQuote1Top = "The Longest Man-Made",
    pullQuote1Bottom = "Structure in the World",
    pullQuote2 = "Its historic and strategic importance is matched only by its architectural significance",
    pullQuote2Author = "UNESCO",
    callout1 = "The best-known sections of the wall were built by the Ming dynasty (1368-1644)",
    callout2 = "During the Ming dynasty, however, bricks were heavily used in many areas of the wall, as were materials such as tiles, lime, and stone",
    videoCaption = "“See China’s Iconic Great Wall From Above | National Geographic.” Youtube, uploaded by National Geographic",
    mapCaption = "Map showing location of Great Wall of China in northern China",
    historyInfo1 = "The Great Wall of China is a series of fortifications that were built across the historical northern borders of ancient Chinese states and Imperial China as protection against various nomadic groups from the Eurasian Steppe. The total length of all sections ever built is over 13,000 miles",
    historyInfo2 = "Several walls were built from as early as the 7th century BCE, with selective stretches later joined together by Qin Shi Huang (220-206 BCE), the first emperor of China. Little of the Qin wall remains. \nLater on, many successive dynasties built and maintained multiple stretches of border walls",
    constructionInfo1 = "Transporting the large quantity of materials required for construction was difficult, so builders always tried to use local resources. Stones from the mountains were used over mountain ranges, while rammed earth was used for construction in the plains. Most of the ancient walls have eroded away over the centuries",
    constructionInfo2 = "Stones cut into rectangular shapes were used for the foundation, inner and outer brims, and gateways of the wall. \nUnder the rule of the Qing dynasty, China's borders extended beyond the walls and Mongolia was annexed into the empire, so construction was discontinued",
    locationInfo1 = "The frontier walls built by different dynasties have multiple courses. Collectively, they stretch from Liaodong in the east to Lop Lake in the west, from the present-day Sino-Russian border in the north to Tao River in the south; along an arc that roughly delineates the edge of the Mongolian steppe",
    locationInfo2 = "Apart from defense, other purposes of the Great Wall have included border controls, allowing the imposition of duties on goods transported along the Silk Road, regulation or encouragement of trade and the control of immigration and emigration",
    startYr = -700,
    endYr = 1644,
    lat = 40.43199751120627,
    lng = 116.57040708482984,
    pullQuote1Author = "",
    videoId = "do1Go22Wu8o",
    events = mapOf(
        700 to "First landmark of the Great Wall began originally as a square wall surrounding the state of Chu. Over the years, additional walls would be built and added to it to expand and connect territory",
        214 to "The first Qin Emperor unifies China and links the wall of the surrounding states of Qin, Yan, and Zhao into the Great Wall of China, taking 10 years to build with hundreds of thousands of laborers",
        121 to "A 20-year construction project was started by the Han emperor to build east and west sections of the wall, including beacons, towers, and castles. Not just for defense, but also to control trade routes like the Silk Road",
        556 to "The Bei Qi kingdom also launched several construction projects, utilizing over 1.8 million workers to repair and extend sections of the wall, adding to its length and even building a second inner wall around Shanxi",
        618 to "The Great Wall was repaired during the Sui Dynasty and used to defend against Tujue attacks. Before and after the Sui Dynasty, the wall saw very little use and fell into disrepair",
        1487 to "Hongzhi Emperor split the walls into north and south lines, eventually shaping it into how it is today. Since then, it has gradually fallen into disrepair and remains mostly unused",
    ),
    searchData = GreatWallSearchData,
    searchSuggestions = GreatWallSearchSuggestions,
    unsplashCollectionId = "Kg_h04xvZEo"
)

data object TajMahal : Wonder(
    title = "Taj Mahal",
    subTitle = "Heaven on Earth",
    regionTitle = "Agra, India",
    artifactCulture = "Mughal",
    artifactGeolocation = "India",
    pullQuote1Top = "Not just a monument,",
    pullQuote1Bottom = "but a symbol of love",
    pullQuote1Author = "Suman Pokhrel",
    pullQuote2 = "The Taj Mahal rises above the banks of the river like a solitary tear suspended on the cheek of time",
    pullQuote2Author = "Rabindranath Tagore",
    callout1 = "The Taj Mahal is distinguished as the finest example of Mughal architecture, a blend of Indian, Persian, and Islamic styles",
    callout2 = "It took the efforts of 22,000 laborers, painters, embroidery artists and stonecutters to shape the Taj Mahal",
    videoCaption = "“India’s Taj Mahal Is an Enduring Monument to Love | National Geographic.” Youtube, uploaded by National Geographic",
    mapCaption = "Map showing location of Taj Mahal in Uttar Pradesh, India",
    historyInfo1 = "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the river Yamuna in the Indian city of Agra. It was commissioned in 1632 CE by the Mughal emperor Shah Jahan (r. 1628-1658) to house the tomb of his favorite wife, Mumtaz Mahal; it also houses the tomb of Shah Jahan himself",
    historyInfo2 = "The tomb is the centerpiece of a 42-acre (17-hectare) complex, which include twin mosque buildings (placed symmetrically on either side of the mausoleum), a guest house, and is set in formal gardens bounded on three sides by walls",
    constructionInfo1 = "The Taj Mahal was constructed using materials from all over India and Asia. It is believed over 1,000 elephants were used to transport building materials\nThe translucent white marble was brought from Rajasthan, the jasper from Punjab, jade and crystal from China. The turquoise was from Tibet and the lapis from Afghanistan, while the sapphire came from Sri Lanka. In all, twenty-eight types of precious and semi-precious stones were inlaid into the white marble",
    constructionInfo2 = "An area of roughly 3 acres was excavated, filled with dirt to reduce seepage, and leveled at 160 ft above riverbank. In the tomb area, wells were dug and filled with stone and rubble to form the footings of the tomb\nThe plinth and tomb took roughly 12 years to complete. The remaining parts of the complex took an additional 10 years",
    locationInfo1 = "India's most famed building, it is situated in the eastern part of the city on the southern bank of the Yamuna River, nearly 1 mile east of the Agra Fort, also on the right bank of the Yamuna",
    locationInfo2 = "The Taj Mahal is built on a parcel of land to the south of the walled city of Agra. Shah Jahan presented Maharaja Jai Singh with a large palace in the center of Agra in exchange for the land",
    startYr = 1632,
    endYr = 1653,
    videoId = "EWkDzLrhpXI",
    lat = 27.17405039840427,
    lng = 78.04211890065208,
    events = mapOf(
        1631 to "Built by Mughal Emperor Shah Jahān to immortalize his deceased wife",
        1647 to "Construction completed. The project involved over 20,000 workers and spanned 42 acres",
        1658 to "There were plans for a second mausoleum for his own remains, but Shah Jahān was imprisoned by his son for the rest of his life in Agra Fort, and this never came to pass",
        1901 to "Lord Curzon and the British Viceroy of India carried out a major restoration to the monument after over 350 years of decay and corrosion due to factory pollution and exhaust",
        1984 to "To protect the structure from Sikh militants and some Hindu nationalist groups, night viewing was banned to tourists. This ban would last 20 years",
        1998 to "Restoration and research program put into action to help preserve the monument",
    ),
    searchData = TajMahalSearchData,
    searchSuggestions = TajMahalSearchSuggestions,
    unsplashCollectionId = "684IRta86_c"
)