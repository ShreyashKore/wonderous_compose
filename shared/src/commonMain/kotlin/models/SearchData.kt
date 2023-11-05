package models

data class SearchData(
    val year: Int,
    val id: Int,
    val title: String,
    val keywords: String,
    val imagePath: String,
    val aspectRatio: Double
) {
    val imageUrl get() = baseImagePath + imagePath;

    companion object {
        const val baseImagePath = "https://images.metmuseum.org/CRDImages/"
    }

    // used by the search helper tool:
    fun write(): String =
        "SearchData($year, $id, '$title', '$keywords', '$imagePath'${
            if (aspectRatio == 0.0) "" else ", $aspectRatio" // TODO: toFixed(2)
        })"
}


val ChichenItzaSearchData = listOf(
    SearchData(
        550,
        317120,
        "Earflare",
        "earflare|jade|stone-ornaments",
        "ao/mobile-large/VS1994_35_582.JPG",
        1.18
    ),
    SearchData(
        700,
        313336,
        "Handle (?)",
        "tube|bone|bone/ivory-sculpture",
        "ao/mobile-large/vs1979_206_1144.jpg",
        0.28
    ),
    SearchData(
        1400,
        317635,
        "Gouge",
        "chisel|stone|stone-implements",
        "ao/mobile-large/vs1994_35_771.jpg",
        3.40
    ),
    SearchData(
        1250,
        318229,
        "Metate Fragment",
        "metate fragment|stone|stone-sculpture",
        "ao/mobile-large/X.180.10.JPG",
        1.52
    ),
    SearchData(
        1400,
        317303,
        "Chisel",
        "chisel|stone|stone-implements",
        "ao/mobile-large/VS1994_35_447.JPG",
        1.43
    ),
    SearchData(
        1100,
        313348,
        "Monkey Vessel",
        "vessel|onyx marble, pyrite, shell|stone-containers",
        "ao/mobile-large/DP-25032-001.jpg",
        0.80
    ),
    SearchData(
        1400,
        317267,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_401.jpg",
        0.58
    ),
    SearchData(
        200,
        312581,
        "House Model",
        "house model|ceramic|ceramics-sculpture",
        "ao/mobile-large/DP-23907-001.jpg",
        0.78
    ),
    SearchData(
        1350,
        312599,
        "Pedestal Bowl",
        "bowl|ceramic|ceramics-containers",
        "ao/mobile-large/DP102175.jpg",
        1.03
    ),
    SearchData(
        850,
        309404,
        "Monumental Figure",
        "figure|limestone|stone-sculpture",
        "ao/mobile-large/DP104844.jpg",
        0.44
    ),
    SearchData(
        750,
        501839,
        "Pottery Rattle",
        "pottery rattle|clay|idiophone-shaken-rattle",
        "mi/mobile-large/DP-23770-001.jpg",
        0.80
    ),
    SearchData(
        1400,
        317227,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_361.jpg",
        0.62
    ),
    SearchData(
        749,
        503940,
        "Double Whistle",
        "double whistle|pottery, paint|aerophone-whistle flute",
        "mi/mobile-large/DT4624a.jpg",
        0.78
    ),
    SearchData(
        400,
        309713,
        "Yoke-Form Vessel",
        "vessel with lid|ceramic|ceramics-containers",
        "ao/mobile-large/DT11169.jpg",
        0.80
    ),
    SearchData(
        1400,
        317234,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_368.jpg",
        0.87
    ),
    SearchData(
        -650,
        313138,
        "Seated Bench Figure",
        "figure|serpentine|stone-sculpture",
        "ao/mobile-large/DT9963.jpg",
        0.80
    ),
    SearchData(
        1400,
        317210,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_343.jpg",
        0.79
    ),
    SearchData(
        700,
        310651,
        "Vessel, Palace Scene",
        "vessel|ceramic|ceramics-containers",
        "ao/mobile-large/1978.412.202_a.JPG",
        0.77
    ),
    SearchData(
        1400,
        317211,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_344.jpg",
        0.68
    ),
    SearchData(
        1400,
        317248,
        "Celt",
        "celt|stone|stone-implements",
        "ao/mobile-large/hz1994_35_382.jpg",
        0.44
    ),
    SearchData(
        -1000,
        314946,
        "Bird Vessel",
        "vessel|ceramic, red ochre|ceramics-containers",
        "ao/mobile-large/DP23080.jpg",
        0.83
    ),
    SearchData(
        700,
        313315,
        "Vessel, Tlaloc Theme",
        "vessel|ceramic|ceramics-containers",
        "ao/mobile-large/1978.412.188_c.JPG",
        0.74
    )
)