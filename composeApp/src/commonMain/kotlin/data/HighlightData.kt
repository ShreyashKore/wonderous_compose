package data


import androidx.compose.runtime.Composable
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder
import org.jetbrains.compose.resources.stringResource

/**
 * Model for artifacts shown in [ui.screens.ArtifactCarouselScreen]
 */
data class HighlightData(
    val title: String,
    val originalImageUrl: String,
    val imageName: String,
    val culture: String,
    val artifactId: String,
    val wonder: Wonder,
    val date: String,
) {
    val id: String
        get() = artifactId

    val subtitle: String
        @Composable get() = wonder.artifactCulture?.let { stringResource(it) } ?: ""

    val imageUrl: String
        get() = "https://firebasestorage.googleapis.com/v0/b/wonderous-compose.appspot.com/o/$imageName?alt=media"

    /**
     * Repository for Highlights
     */
    companion object {
        fun fromId(id: String?): HighlightData? {
            if (id == null) return null
            return highlights.firstOrNull { it.id == id }
        }


        fun forWonder(wonder: Wonder): List<HighlightData> =
            highlights.filter { it.wonder == wonder }


        fun getAll(): List<HighlightData> = highlights

    }
}


private val highlights = listOf(
    // chichenItza
    HighlightData(
        title = "Double Whistle",
        wonder = ChichenItza,
        artifactId = "503940",
        culture = "Mayan",
        imageName = "DT4624a.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/mi/original/DT4624a.jpg",
        date = "7th–9th century"
    ),
    HighlightData(
        title = "Seated Female Figure",
        wonder = ChichenItza,
        artifactId = "312595",
        culture = "Maya",
        imageName = "DP-12659-001.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP-12659-001.jpg",
        date = "6th–9th century"
    ),
    HighlightData(
        title = "Censer Support",
        wonder = ChichenItza,
        artifactId = "310551",
        culture = "Maya",
        imageName = "DP102949.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP102949.jpg",
        date = "mid-7th–9th century"
    ),
    HighlightData(
        title = "Tripod Plate",
        wonder = ChichenItza,
        artifactId = "316304",
        culture = "Maya",
        imageName = "DP219258.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP219258.jpg",
        date = "9th–10th century"
    ),
    HighlightData(
        title = "Costumed Figure",
        wonder = ChichenItza,
        artifactId = "313151",
        culture = "Maya",
        imageName = "1979.206.953_a.JPG",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/1979.206.953_a.JPG",
        date = "7th–8th century"
    ),
    HighlightData(
        title = "Head of a Rain God",
        wonder = ChichenItza,
        artifactId = "310480",
        culture = "Maya",
        imageName = "DP102948.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP102948.jpg",
        date = "10th–11th century"
    ),

    // christRedeemer
    HighlightData(
        title = "[Studio Portrait: Male Street Vendor Holding Box of Flowers, Brazil]",
        wonder = ChristRedeemer,
        artifactId = "764815",
        culture = "",
        imageName = "DP-15801-131.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ph/original/DP-15801-131.jpg",
        date = "1864–66"
    ),
    HighlightData(
        title = "Rattle",
        wonder = ChristRedeemer,
        artifactId = "502019",
        culture = "Native American (Brazilian)",
        imageName = "midp89.4.1453.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/mi/original/midp89.4.1453.jpg",
        date = "19th century"
    ),
    HighlightData(
        title = "[Studio Portrait: Two Males Wearing Hats and Ponchos, Brazil]",
        wonder = ChristRedeemer,
        artifactId = "764814",
        culture = "",
        imageName = "DP-15801-129.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ph/original/DP-15801-129.jpg",
        date = "1864–66"
    ),
    HighlightData(
        title = "[Studio Portrait: Female Street Vendor Seated Wearing Turban, Brazil]",
        wonder = ChristRedeemer,
        artifactId = "764816",
        culture = "",
        imageName = "DP-15801-133.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ph/original/DP-15801-133.jpg",
        date = "1864–66"
    ),
    HighlightData(
        title = "Pluriarc",
        wonder = ChristRedeemer,
        artifactId = "501319",
        culture = "African American (Brazil - Afro-Brazilian?)",
        imageName = "midp89.4.703.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/mi/original/midp89.4.703.jpg",
        date = "late 19th century"
    ),

    // colosseum
    HighlightData(
        title = "Marble portrait of a young woman",
        wonder = Colosseum,
        artifactId = "251350",
        culture = "Roman",
        imageName = "DP331280.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP331280.jpg",
        date = "A.D. 150–175"
    ),
    HighlightData(
        title = "Silver mirror",
        wonder = Colosseum,
        artifactId = "255960",
        culture = "Roman",
        imageName = "DP145605.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP145605.jpg",
        date = "4th century A.D."
    ),
    HighlightData(
        title = "Marble portrait of the emperor Augustus",
        wonder = Colosseum,
        artifactId = "247993",
        culture = "Roman",
        imageName = "DP337220.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP337220.jpg",
        date = "ca. A.D. 14–37"
    ),
    HighlightData(
        title = "Terracotta medallion",
        wonder = Colosseum,
        artifactId = "250464",
        culture = "Roman",
        imageName = "DP105842.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP105842.jpg",
        date = "late 2nd–early 3rd century A.D."
    ),
    HighlightData(
        title = "Marble head and torso of Athena",
        wonder = Colosseum,
        artifactId = "251476",
        culture = "Roman",
        imageName = "DP357289.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP357289.jpg",
        date = "1st–2nd century A.D."
    ),
    HighlightData(
        title = "Silver mirror",
        wonder = Colosseum,
        artifactId = "255960",
        culture = "Roman",
        imageName = "DP145605.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/gr/original/DP145605.jpg",
        date = "4th century A.D."
    ),

    // greatWall
    HighlightData(
        title = "Cape",
        wonder = GreatWall,
        artifactId = "79091",
        culture = "French",
        imageName = "DT2183.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ci/original/DT2183.jpg",
        date = "second half 16th century"
    ),
    HighlightData(
        title = "Censer in the form of a mythical beast",
        wonder = GreatWall,
        artifactId = "781812",
        culture = "China",
        imageName = "DP-17100-001.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP-17100-001.jpg",
        date = "early 17th century"
    ),
    HighlightData(
        title = "Dish with peafowls and peonies",
        wonder = GreatWall,
        artifactId = "40213",
        culture = "China",
        imageName = "DP704217.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP704217.jpg",
        date = "early 15th century"
    ),
    HighlightData(
        title = "Base for a mandala",
        wonder = GreatWall,
        artifactId = "40765",
        culture = "China",
        imageName = "DP229015.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP229015.jpg",
        date = "15th century"
    ),
    HighlightData(
        title = "Bodhisattva Manjushri as Tikshna-Manjushri (Minjie Wenshu)",
        wonder = GreatWall,
        artifactId = "57612",
        culture = "China",
        imageName = "DP164061.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP164061.jpg",
        date = ""
    ),
    HighlightData(
        title = "Tripod incense burner with lid",
        wonder = GreatWall,
        artifactId = "666573",
        culture = "China",
        imageName = "DP356342.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP356342.jpg",
        date = "early 15th century"
    ),

    // machuPicchu
    HighlightData(
        title = "Face Beaker",
        wonder = MachuPicchu,
        artifactId = "313295",
        culture = "Inca",
        imageName = "DP-27120-001.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP-27120-001.jpg",
        date = "14th–early 16th century"
    ),
    HighlightData(
        title = "Feathered Bag",
        wonder = MachuPicchu,
        artifactId = "316926",
        culture = "Inca",
        imageName = "DP158704.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP158704.jpg",
        date = "15th–early 16th century"
    ),
    HighlightData(
        title = "Female Figurine",
        wonder = MachuPicchu,
        artifactId = "309944",
        culture = "Inca",
        imageName = "DP-13440-023.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP-13440-023.jpg",
        date = "1400–1533"
    ),
    HighlightData(
        title = "Stirrup Spout Bottle with Felines",
        wonder = MachuPicchu,
        artifactId = "309436",
        culture = "Moche",
        imageName = "67.92.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/67.92.jpg",
        date = "4th–7th century"
    ),
    HighlightData(
        title = "Camelid figurine",
        wonder = MachuPicchu,
        artifactId = "309960",
        culture = "Inca",
        imageName = "DP-13440-031.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP-13440-031.jpg",
        date = "1400–1533"
    ),
    HighlightData(
        title = "Temple Model",
        wonder = MachuPicchu,
        artifactId = "316873",
        culture = "Aztec",
        imageName = "DP341942.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/ao/original/DP341942.jpg",
        date = "1400–1521"
    ),

    // petra
    HighlightData(
        title = "Unguentarium",
        wonder = Petra,
        artifactId = "325900",
        culture = "Nabataean",
        imageName = "ME67_246_19.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_19.jpg",
        date = "ca. 1st century A.D."
    ),
    HighlightData(
        title = "Cooking pot",
        wonder = Petra,
        artifactId = "325902",
        culture = "Nabataean",
        imageName = "ME67_246_21.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_21.jpg",
        date = "ca. 1st century A.D."
    ),
    HighlightData(
        title = "Lamp",
        wonder = Petra,
        artifactId = "325919",
        culture = "Nabataean",
        imageName = "ME67_246_38.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_38.jpg",
        date = "ca. 1st century A.D."
    ),
    HighlightData(
        title = "Bowl",
        wonder = Petra,
        artifactId = "325884",
        culture = "Nabataean",
        imageName = "ME67_246_3.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_3.jpg",
        date = "ca. 1st century A.D."
    ),
    HighlightData(
        title = "Small lamp",
        wonder = Petra,
        artifactId = "325887",
        culture = "Nabataean",
        imageName = "ME67_246_6.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_6.jpg",
        date = "ca. 1st century A.D."
    ),
    HighlightData(
        title = "Male figurine",
        wonder = Petra,
        artifactId = "325891",
        culture = "Nabataean",
        imageName = "ME67_246_10.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/an/original/ME67_246_10.jpg",
        date = "ca. 1st century A.D."
    ),

    // pyramidsGiza
    HighlightData(
        title = "Guardian Figure",
        wonder = PyramidsGiza,
        artifactId = "543864",
        culture = "",
        imageName = "DP330260.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/DP330260.jpg",
        date = "ca. 1919–1885 B.C."
    ),
    HighlightData(
        title = "Relief fragment",
        wonder = PyramidsGiza,
        artifactId = "546488",
        culture = "",
        imageName = "LC-34_1_183_EGDP033257.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/LC-34_1_183_EGDP033257.jpg",
        date = "ca. 1981–1640 B.C."
    ),
    HighlightData(
        title = "Ring with Uninscribed Scarab",
        wonder = PyramidsGiza,
        artifactId = "557137",
        culture = "",
        imageName = "15.3.205_EGDP015425.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/15.3.205_EGDP015425.jpg",
        date = "ca. 1850–1640 B.C."
    ),
    HighlightData(
        title = "Nikare as a scribe",
        wonder = PyramidsGiza,
        artifactId = "543900",
        culture = "",
        imageName = "DP330276.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/DP330276.jpg",
        date = "ca. 1919–1885 B.C."
    ),

    HighlightData(
        title = "Seated Statue of King Menkaure",
        wonder = PyramidsGiza,
        artifactId = "543935",
        culture = "",
        imageName = "DP109397.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/DP109397.jpg",
        date = "ca. 2490–2472 B.C."
    ),
    HighlightData(
        title = "Floral collar from Tutankhamun's Embalming Cache",
        wonder = PyramidsGiza,
        artifactId = "544782",
        culture = "",
        imageName = "DP225343.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/eg/original/DP225343.jpg",
        date = "ca. 1336–1327 B.C."
    ),

// tajMahal
    HighlightData(
        title = "Mango-Shaped Flask",
        wonder = TajMahal,
        artifactId = "453341",
        culture = "",
        imageName = "DP240307.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/is/original/DP240307.jpg",
        date = "mid-17th century"
    ),
    HighlightData(
        title = "Base for a Water Pipe (Huqqa) with Irises",
        wonder = TajMahal,
        artifactId = "453243",
        culture = "",
        imageName = "DP214317.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/is/original/DP214317.jpg",
        date = "late 17th century"
    ),
    HighlightData(
        title = "Plate",
        wonder = TajMahal,
        artifactId = "73309",
        culture = "India (Gujarat)",
        imageName = "DP138506.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP138506.jpg",
        date = "mid-16th–17th century"
    ),
    HighlightData(
        title = "Helmet",
        wonder = TajMahal,
        artifactId = "24932",
        culture = "Indian, Mughal",
        imageName = "1988.147_007mar2015.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/aa/original/1988.147_007mar2015.jpg",
        date = "18th century"
    ),
    HighlightData(
        title = "Jewelled plate",
        wonder = TajMahal,
        artifactId = "56230",
        culture = "India",
        imageName = "DP-14153-029.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/as/original/DP-14153-029.jpg",
        date = "18th–19th century"
    ),
    HighlightData(
        title = "Shirt of Mail and Plate of Emperor Shah Jahan (reigned 1624–58)",
        wonder = TajMahal,
        artifactId = "35633",
        culture = "Indian",
        imageName = "DP219616.jpg",
        originalImageUrl = "https://images.metmuseum.org/CRDImages/aa/original/DP219616.jpg",
        date = "dated A.H. 1042/A.D. 1632–33"
    )
)
