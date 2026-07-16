package dev.shreyash.wonderouscompose.models

import androidx.compose.runtime.Composable
import dev.shreyash.wonderouscompose.data.search_data.ChichenSearchData
import dev.shreyash.wonderouscompose.data.search_data.ChichenSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.ChristRedeemerSearchData
import dev.shreyash.wonderouscompose.data.search_data.ChristRedeemerSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.ColosseumSearchData
import dev.shreyash.wonderouscompose.data.search_data.ColosseumSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.GreatWallSearchData
import dev.shreyash.wonderouscompose.data.search_data.GreatWallSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.MachuPicchuSearchData
import dev.shreyash.wonderouscompose.data.search_data.MachuPicchuSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.PetraSearchData
import dev.shreyash.wonderouscompose.data.search_data.PetraSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.PyramidsGizaSearchData
import dev.shreyash.wonderouscompose.data.search_data.PyramidsGizaSearchSuggestions
import dev.shreyash.wonderouscompose.data.search_data.TajMahalSearchData
import dev.shreyash.wonderouscompose.data.search_data.TajMahalSearchSuggestions
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import wonderouscompose.shared.generated.resources.Res
import wonderouscompose.shared.generated.resources.chichenItza1100ce
import wonderouscompose.shared.generated.resources.chichenItza1527ce
import wonderouscompose.shared.generated.resources.chichenItza1535ce
import wonderouscompose.shared.generated.resources.chichenItza600ce
import wonderouscompose.shared.generated.resources.chichenItza832ce
import wonderouscompose.shared.generated.resources.chichenItza998ce
import wonderouscompose.shared.generated.resources.chichenItzaArtifactGeolocation
import wonderouscompose.shared.generated.resources.chichenItzaCallout1
import wonderouscompose.shared.generated.resources.chichenItzaCallout2
import wonderouscompose.shared.generated.resources.chichenItzaConstructionInfo1
import wonderouscompose.shared.generated.resources.chichenItzaConstructionInfo2
import wonderouscompose.shared.generated.resources.chichenItzaHistoryInfo1
import wonderouscompose.shared.generated.resources.chichenItzaHistoryInfo2
import wonderouscompose.shared.generated.resources.chichenItzaLocationInfo1
import wonderouscompose.shared.generated.resources.chichenItzaLocationInfo2
import wonderouscompose.shared.generated.resources.chichenItzaPullQuote1Bottom
import wonderouscompose.shared.generated.resources.chichenItzaPullQuote1Top
import wonderouscompose.shared.generated.resources.chichenItzaPullQuote2
import wonderouscompose.shared.generated.resources.chichenItzaPullQuote2Author
import wonderouscompose.shared.generated.resources.chichenItzaRegionTitle
import wonderouscompose.shared.generated.resources.chichenItzaSubTitle
import wonderouscompose.shared.generated.resources.chichenItzaTitle
import wonderouscompose.shared.generated.resources.chichenItzaVideoCaption
import wonderouscompose.shared.generated.resources.christRedeemer1850ce
import wonderouscompose.shared.generated.resources.christRedeemer1921ce
import wonderouscompose.shared.generated.resources.christRedeemer1922ce
import wonderouscompose.shared.generated.resources.christRedeemer1926ce
import wonderouscompose.shared.generated.resources.christRedeemer1931ce
import wonderouscompose.shared.generated.resources.christRedeemer2006ce
import wonderouscompose.shared.generated.resources.christRedeemerArtifactGeolocation
import wonderouscompose.shared.generated.resources.christRedeemerCallout1
import wonderouscompose.shared.generated.resources.christRedeemerCallout2
import wonderouscompose.shared.generated.resources.christRedeemerConstructionInfo1
import wonderouscompose.shared.generated.resources.christRedeemerConstructionInfo2
import wonderouscompose.shared.generated.resources.christRedeemerHistoryInfo1
import wonderouscompose.shared.generated.resources.christRedeemerHistoryInfo2
import wonderouscompose.shared.generated.resources.christRedeemerLocationInfo1
import wonderouscompose.shared.generated.resources.christRedeemerLocationInfo2
import wonderouscompose.shared.generated.resources.christRedeemerMapCaption
import wonderouscompose.shared.generated.resources.christRedeemerPullQuote1Bottom
import wonderouscompose.shared.generated.resources.christRedeemerPullQuote1Top
import wonderouscompose.shared.generated.resources.christRedeemerPullQuote2
import wonderouscompose.shared.generated.resources.christRedeemerPullQuote2Author
import wonderouscompose.shared.generated.resources.christRedeemerRegionTitle
import wonderouscompose.shared.generated.resources.christRedeemerSubTitle
import wonderouscompose.shared.generated.resources.christRedeemerTitle
import wonderouscompose.shared.generated.resources.christRedeemerVideoCaption
import wonderouscompose.shared.generated.resources.colosseum1140ce
import wonderouscompose.shared.generated.resources.colosseum1490ce
import wonderouscompose.shared.generated.resources.colosseum1829ce
import wonderouscompose.shared.generated.resources.colosseum1990ce
import wonderouscompose.shared.generated.resources.colosseum70ce
import wonderouscompose.shared.generated.resources.colosseum82ce
import wonderouscompose.shared.generated.resources.colosseumArtifactCulture
import wonderouscompose.shared.generated.resources.colosseumArtifactGeolocation
import wonderouscompose.shared.generated.resources.colosseumCallout1
import wonderouscompose.shared.generated.resources.colosseumCallout2
import wonderouscompose.shared.generated.resources.colosseumConstructionInfo1
import wonderouscompose.shared.generated.resources.colosseumConstructionInfo2
import wonderouscompose.shared.generated.resources.colosseumHistoryInfo1
import wonderouscompose.shared.generated.resources.colosseumHistoryInfo2
import wonderouscompose.shared.generated.resources.colosseumLocationInfo1
import wonderouscompose.shared.generated.resources.colosseumLocationInfo2
import wonderouscompose.shared.generated.resources.colosseumMapCaption
import wonderouscompose.shared.generated.resources.colosseumPullQuote1Bottom
import wonderouscompose.shared.generated.resources.colosseumPullQuote1Top
import wonderouscompose.shared.generated.resources.colosseumPullQuote2
import wonderouscompose.shared.generated.resources.colosseumPullQuote2Author
import wonderouscompose.shared.generated.resources.colosseumRegionTitle
import wonderouscompose.shared.generated.resources.colosseumSubTitle
import wonderouscompose.shared.generated.resources.colosseumTitle
import wonderouscompose.shared.generated.resources.colosseumVideoCaption
import wonderouscompose.shared.generated.resources.greatWall121bce
import wonderouscompose.shared.generated.resources.greatWall1487ce
import wonderouscompose.shared.generated.resources.greatWall214bce
import wonderouscompose.shared.generated.resources.greatWall556ce
import wonderouscompose.shared.generated.resources.greatWall618ce
import wonderouscompose.shared.generated.resources.greatWall700bce
import wonderouscompose.shared.generated.resources.greatWallArtifactCulture
import wonderouscompose.shared.generated.resources.greatWallArtifactGeolocation
import wonderouscompose.shared.generated.resources.greatWallCallout1
import wonderouscompose.shared.generated.resources.greatWallCallout2
import wonderouscompose.shared.generated.resources.greatWallConstructionInfo1
import wonderouscompose.shared.generated.resources.greatWallConstructionInfo2
import wonderouscompose.shared.generated.resources.greatWallHistoryInfo1
import wonderouscompose.shared.generated.resources.greatWallHistoryInfo2
import wonderouscompose.shared.generated.resources.greatWallLocationInfo1
import wonderouscompose.shared.generated.resources.greatWallLocationInfo2
import wonderouscompose.shared.generated.resources.greatWallMapCaption
import wonderouscompose.shared.generated.resources.greatWallPullQuote1Bottom
import wonderouscompose.shared.generated.resources.greatWallPullQuote1Top
import wonderouscompose.shared.generated.resources.greatWallPullQuote2
import wonderouscompose.shared.generated.resources.greatWallPullQuote2Author
import wonderouscompose.shared.generated.resources.greatWallRegionTitle
import wonderouscompose.shared.generated.resources.greatWallSubTitle
import wonderouscompose.shared.generated.resources.greatWallTitle
import wonderouscompose.shared.generated.resources.greatWallVideoCaption
import wonderouscompose.shared.generated.resources.machuPicchu1438ce
import wonderouscompose.shared.generated.resources.machuPicchu1572ce
import wonderouscompose.shared.generated.resources.machuPicchu1867ce
import wonderouscompose.shared.generated.resources.machuPicchu1911ce
import wonderouscompose.shared.generated.resources.machuPicchu1964ce
import wonderouscompose.shared.generated.resources.machuPicchu1997ce
import wonderouscompose.shared.generated.resources.machuPicchuArtifactCulture
import wonderouscompose.shared.generated.resources.machuPicchuArtifactGeolocation
import wonderouscompose.shared.generated.resources.machuPicchuCallout1
import wonderouscompose.shared.generated.resources.machuPicchuCallout2
import wonderouscompose.shared.generated.resources.machuPicchuConstructionInfo1
import wonderouscompose.shared.generated.resources.machuPicchuConstructionInfo2
import wonderouscompose.shared.generated.resources.machuPicchuHistoryInfo1
import wonderouscompose.shared.generated.resources.machuPicchuHistoryInfo2
import wonderouscompose.shared.generated.resources.machuPicchuLocationInfo1
import wonderouscompose.shared.generated.resources.machuPicchuLocationInfo2
import wonderouscompose.shared.generated.resources.machuPicchuMapCaption
import wonderouscompose.shared.generated.resources.machuPicchuPullQuote1Author
import wonderouscompose.shared.generated.resources.machuPicchuPullQuote1Bottom
import wonderouscompose.shared.generated.resources.machuPicchuPullQuote1Top
import wonderouscompose.shared.generated.resources.machuPicchuPullQuote2
import wonderouscompose.shared.generated.resources.machuPicchuPullQuote2Author
import wonderouscompose.shared.generated.resources.machuPicchuRegionTitle
import wonderouscompose.shared.generated.resources.machuPicchuSubTitle
import wonderouscompose.shared.generated.resources.machuPicchuTitle
import wonderouscompose.shared.generated.resources.machuPicchuVideoCaption
import wonderouscompose.shared.generated.resources.petra106bce
import wonderouscompose.shared.generated.resources.petra1200bce
import wonderouscompose.shared.generated.resources.petra1812ce
import wonderouscompose.shared.generated.resources.petra1958ce
import wonderouscompose.shared.generated.resources.petra1989ce
import wonderouscompose.shared.generated.resources.petra551ce
import wonderouscompose.shared.generated.resources.petraArtifactCulture
import wonderouscompose.shared.generated.resources.petraArtifactGeolocation
import wonderouscompose.shared.generated.resources.petraCallout1
import wonderouscompose.shared.generated.resources.petraCallout2
import wonderouscompose.shared.generated.resources.petraConstructionInfo1
import wonderouscompose.shared.generated.resources.petraConstructionInfo2
import wonderouscompose.shared.generated.resources.petraHistoryInfo1
import wonderouscompose.shared.generated.resources.petraHistoryInfo2
import wonderouscompose.shared.generated.resources.petraLocationInfo1
import wonderouscompose.shared.generated.resources.petraLocationInfo2
import wonderouscompose.shared.generated.resources.petraMapCaption
import wonderouscompose.shared.generated.resources.petraPullQuote1Author
import wonderouscompose.shared.generated.resources.petraPullQuote1Bottom
import wonderouscompose.shared.generated.resources.petraPullQuote1Top
import wonderouscompose.shared.generated.resources.petraPullQuote2
import wonderouscompose.shared.generated.resources.petraPullQuote2Author
import wonderouscompose.shared.generated.resources.petraRegionTitle
import wonderouscompose.shared.generated.resources.petraSubTitle
import wonderouscompose.shared.generated.resources.petraTitle
import wonderouscompose.shared.generated.resources.petraVideoCaption
import wonderouscompose.shared.generated.resources.pyramidsGiza1925ce
import wonderouscompose.shared.generated.resources.pyramidsGiza1979ce
import wonderouscompose.shared.generated.resources.pyramidsGiza1990ce
import wonderouscompose.shared.generated.resources.pyramidsGiza2465bce
import wonderouscompose.shared.generated.resources.pyramidsGiza2575bce
import wonderouscompose.shared.generated.resources.pyramidsGiza443bce
import wonderouscompose.shared.generated.resources.pyramidsGizaArtifactCulture
import wonderouscompose.shared.generated.resources.pyramidsGizaArtifactGeolocation
import wonderouscompose.shared.generated.resources.pyramidsGizaCallout1
import wonderouscompose.shared.generated.resources.pyramidsGizaCallout2
import wonderouscompose.shared.generated.resources.pyramidsGizaConstructionInfo1
import wonderouscompose.shared.generated.resources.pyramidsGizaConstructionInfo2
import wonderouscompose.shared.generated.resources.pyramidsGizaHistoryInfo1
import wonderouscompose.shared.generated.resources.pyramidsGizaHistoryInfo2
import wonderouscompose.shared.generated.resources.pyramidsGizaLocationInfo1
import wonderouscompose.shared.generated.resources.pyramidsGizaLocationInfo2
import wonderouscompose.shared.generated.resources.pyramidsGizaMapCaption
import wonderouscompose.shared.generated.resources.pyramidsGizaPullQuote1Bottom
import wonderouscompose.shared.generated.resources.pyramidsGizaPullQuote1Top
import wonderouscompose.shared.generated.resources.pyramidsGizaPullQuote2
import wonderouscompose.shared.generated.resources.pyramidsGizaPullQuote2Author
import wonderouscompose.shared.generated.resources.pyramidsGizaRegionTitle
import wonderouscompose.shared.generated.resources.pyramidsGizaSubTitle
import wonderouscompose.shared.generated.resources.pyramidsGizaTitle
import wonderouscompose.shared.generated.resources.pyramidsGizaVideoCaption
import wonderouscompose.shared.generated.resources.tajMahal1631ce
import wonderouscompose.shared.generated.resources.tajMahal1647ce
import wonderouscompose.shared.generated.resources.tajMahal1658ce
import wonderouscompose.shared.generated.resources.tajMahal1901ce
import wonderouscompose.shared.generated.resources.tajMahal1984ce
import wonderouscompose.shared.generated.resources.tajMahal1998ce
import wonderouscompose.shared.generated.resources.tajMahalArtifactCulture
import wonderouscompose.shared.generated.resources.tajMahalArtifactGeolocation
import wonderouscompose.shared.generated.resources.tajMahalCallout1
import wonderouscompose.shared.generated.resources.tajMahalCallout2
import wonderouscompose.shared.generated.resources.tajMahalConstructionInfo1
import wonderouscompose.shared.generated.resources.tajMahalConstructionInfo2
import wonderouscompose.shared.generated.resources.tajMahalHistoryInfo1
import wonderouscompose.shared.generated.resources.tajMahalHistoryInfo2
import wonderouscompose.shared.generated.resources.tajMahalLocationInfo1
import wonderouscompose.shared.generated.resources.tajMahalLocationInfo2
import wonderouscompose.shared.generated.resources.tajMahalMapCaption
import wonderouscompose.shared.generated.resources.tajMahalPullQuote1Author
import wonderouscompose.shared.generated.resources.tajMahalPullQuote1Bottom
import wonderouscompose.shared.generated.resources.tajMahalPullQuote1Top
import wonderouscompose.shared.generated.resources.tajMahalPullQuote2
import wonderouscompose.shared.generated.resources.tajMahalPullQuote2Author
import wonderouscompose.shared.generated.resources.tajMahalRegionTitle
import wonderouscompose.shared.generated.resources.tajMahalSubTitle
import wonderouscompose.shared.generated.resources.tajMahalTitle
import wonderouscompose.shared.generated.resources.tajMahalVideoCaption


sealed class Wonder(
    val name: String,
    val title: StringResource,
    val subTitle: StringResource,
    val regionTitle: StringResource,
    val historyInfo1: StringResource,
    val historyInfo2: StringResource,
    val constructionInfo1: StringResource,
    val constructionInfo2: StringResource,
    val locationInfo1: StringResource,
    val locationInfo2: StringResource,
    val pullQuote1Top: StringResource,
    val pullQuote1Bottom: StringResource,
    val pullQuote1Author: StringResource? = null,
    val pullQuote2: StringResource,
    val pullQuote2Author: StringResource,
    val callout1: StringResource,
    val callout2: StringResource,
    val unsplashCollectionId: String,
    val videoId: String,
    val videoCaption: StringResource,
    val mapCaption: StringResource? = null,
    val imageIds: List<String> = emptyList(),
    val facts: List<StringResource> = emptyList(),
    val startYr: Int = 0,
    val endYr: Int = 0,
    val artifactStartYr: Int = 0,
    val artifactEndYr: Int = 0,
    val artifactCulture: StringResource? = null,
    val artifactGeolocation: StringResource,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val highlightArtifacts: List<String> = emptyList(), // IDs used to assemble HighlightsData: String, should not be used directly
    val hiddenArtifacts: List<String> = emptyList(), // IDs used to assemble CollectibleData: String, should not be used directly
    val events: Map<Int, StringResource>,
    val searchData: List<SearchData> = emptyList(),
    val searchSuggestions: List<String> = emptyList(),
) {
    val latLng get() = LatLng(lat, lng)

    val titleWithBreaks: String @Composable get() = stringResource(title).replaceFirst(' ', '\n')

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
        it.name.lowercase() == name.lowercase()
    } ?: GreatWall
}

data object ChichenItza : Wonder(
    name = "ChichenItza",
    title = Res.string.chichenItzaTitle,
    subTitle = Res.string.chichenItzaSubTitle,
    regionTitle = Res.string.chichenItzaRegionTitle,
    historyInfo1 = Res.string.chichenItzaHistoryInfo1,
    historyInfo2 = Res.string.chichenItzaHistoryInfo2,
    constructionInfo1 = Res.string.chichenItzaConstructionInfo1,
    constructionInfo2 = Res.string.chichenItzaConstructionInfo2,
    locationInfo1 = Res.string.chichenItzaLocationInfo1,
    locationInfo2 = Res.string.chichenItzaLocationInfo2,
    pullQuote1Top = Res.string.chichenItzaPullQuote1Top,
    pullQuote1Bottom = Res.string.chichenItzaPullQuote1Bottom,
    pullQuote1Author = null,
    pullQuote2 = Res.string.chichenItzaPullQuote2,
    pullQuote2Author = Res.string.chichenItzaPullQuote2Author,
    callout1 = Res.string.chichenItzaCallout1,
    callout2 = Res.string.chichenItzaCallout2,
    unsplashCollectionId = "SUK0tuMnLLw",
    videoId = "Q6eBJjdca14",
    videoCaption = Res.string.chichenItzaVideoCaption,
    startYr = 550,
    endYr = 1550,
    artifactStartYr = 500,
    artifactEndYr = 1600,
    artifactGeolocation = Res.string.chichenItzaArtifactGeolocation,
    lat = 20.68346184201756,
    lng = -88.56769676930931,
    events = mapOf(
        600 to Res.string.chichenItza600ce,
        832 to Res.string.chichenItza832ce,
        998 to Res.string.chichenItza998ce,
        1100 to Res.string.chichenItza1100ce,
        1527 to Res.string.chichenItza1527ce,
        1535 to Res.string.chichenItza1535ce,
    ),
    searchData = ChichenSearchData,
    searchSuggestions = ChichenSearchSuggestions
)

data object ChristRedeemer : Wonder(
    name = "ChristRedeemer",
    title = Res.string.christRedeemerTitle,
    subTitle = Res.string.christRedeemerSubTitle,
    regionTitle = Res.string.christRedeemerRegionTitle,
    historyInfo1 = Res.string.christRedeemerHistoryInfo1,
    historyInfo2 = Res.string.christRedeemerHistoryInfo2,
    constructionInfo1 = Res.string.christRedeemerConstructionInfo1,
    constructionInfo2 = Res.string.christRedeemerConstructionInfo2,
    locationInfo1 = Res.string.christRedeemerLocationInfo1,
    locationInfo2 = Res.string.christRedeemerLocationInfo2,
    pullQuote1Top = Res.string.christRedeemerPullQuote1Top,
    pullQuote1Bottom = Res.string.christRedeemerPullQuote1Bottom,
    pullQuote1Author = null,
    pullQuote2 = Res.string.christRedeemerPullQuote2,
    pullQuote2Author = Res.string.christRedeemerPullQuote2Author,
    callout1 = Res.string.christRedeemerCallout1,
    callout2 = Res.string.christRedeemerCallout2,
    unsplashCollectionId = "dPgX5iK8Ufo",
    videoId = "k_615AauSds",
    videoCaption = Res.string.christRedeemerVideoCaption,
    mapCaption = Res.string.christRedeemerMapCaption,
    startYr = 1922,
    endYr = 1931,
    artifactStartYr = 1600,
    artifactEndYr = 2100,
    artifactGeolocation = Res.string.christRedeemerArtifactGeolocation,
    lat = -22.95238891944396,
    lng = -43.21045520611561,
    events = mapOf(
        1850 to Res.string.christRedeemer1850ce,
        1921 to Res.string.christRedeemer1921ce,
        1922 to Res.string.christRedeemer1922ce,
        1926 to Res.string.christRedeemer1926ce,
        1931 to Res.string.christRedeemer1931ce,
        2006 to Res.string.christRedeemer2006ce,
    ),
    searchData = ChristRedeemerSearchData,
    searchSuggestions = ChristRedeemerSearchSuggestions
)

data object Petra : Wonder(
    name = "Petra",
    title = Res.string.petraTitle,
    subTitle = Res.string.petraSubTitle,
    regionTitle = Res.string.petraRegionTitle,
    historyInfo1 = Res.string.petraHistoryInfo1,
    historyInfo2 = Res.string.petraHistoryInfo2,
    constructionInfo1 = Res.string.petraConstructionInfo1,
    constructionInfo2 = Res.string.petraConstructionInfo2,
    locationInfo1 = Res.string.petraLocationInfo1,
    locationInfo2 = Res.string.petraLocationInfo2,
    pullQuote1Top = Res.string.petraPullQuote1Top,
    pullQuote1Bottom = Res.string.petraPullQuote1Bottom,
    pullQuote1Author = Res.string.petraPullQuote1Author,
    pullQuote2 = Res.string.petraPullQuote2,
    pullQuote2Author = Res.string.petraPullQuote2Author,
    callout1 = Res.string.petraCallout1,
    callout2 = Res.string.petraCallout2,
    unsplashCollectionId = "qWQJbDvCMW8",
    videoId = "ezDiSkOU0wc",
    videoCaption = Res.string.petraVideoCaption,
    mapCaption = Res.string.petraMapCaption,
    startYr = -312,
    endYr = 100,
    artifactStartYr = -500,
    artifactEndYr = 500,
    artifactCulture = Res.string.petraArtifactCulture,
    artifactGeolocation = Res.string.petraArtifactGeolocation,
    lat = 30.328830750209903,
    lng = 35.44398203484667,
    events = mapOf(
        -1200 to Res.string.petra1200bce,
        -106 to Res.string.petra106bce,
        551 to Res.string.petra551ce,
        1812 to Res.string.petra1812ce,
        1958 to Res.string.petra1958ce,
        1989 to Res.string.petra1989ce,
    ),
    searchData = PetraSearchData,
    searchSuggestions = PetraSearchSuggestions,
)

data object Colosseum : Wonder(
    name = "Colosseum",
    title = Res.string.colosseumTitle,
    subTitle = Res.string.colosseumSubTitle,
    regionTitle = Res.string.colosseumRegionTitle,
    historyInfo1 = Res.string.colosseumHistoryInfo1,
    historyInfo2 = Res.string.colosseumHistoryInfo2,
    constructionInfo1 = Res.string.colosseumConstructionInfo1,
    constructionInfo2 = Res.string.colosseumConstructionInfo2,
    locationInfo1 = Res.string.colosseumLocationInfo1,
    locationInfo2 = Res.string.colosseumLocationInfo2,
    pullQuote1Top = Res.string.colosseumPullQuote1Top,
    pullQuote1Bottom = Res.string.colosseumPullQuote1Bottom,
    pullQuote1Author = null,
    pullQuote2 = Res.string.colosseumPullQuote2,
    pullQuote2Author = Res.string.colosseumPullQuote2Author,
    callout1 = Res.string.colosseumCallout1,
    callout2 = Res.string.colosseumCallout2,
    unsplashCollectionId = "VPdti8Kjq9o",
    videoId = "GXoEpNjgKzg",
    videoCaption = Res.string.colosseumVideoCaption,
    mapCaption = Res.string.colosseumMapCaption,
    startYr = 70,
    endYr = 80,
    artifactStartYr = 0,
    artifactEndYr = 500,
    artifactCulture = Res.string.colosseumArtifactCulture,
    artifactGeolocation = Res.string.colosseumArtifactGeolocation,
    lat = 41.890242126393495,
    lng = 12.492349361871392,
    events = mapOf(
        70 to Res.string.colosseum70ce,
        82 to Res.string.colosseum82ce,
        1140 to Res.string.colosseum1140ce,
        1490 to Res.string.colosseum1490ce,
        1829 to Res.string.colosseum1829ce,
        1990 to Res.string.colosseum1990ce,
    ),
    searchData = ColosseumSearchData,
    searchSuggestions = ColosseumSearchSuggestions,
)

data object PyramidsGiza : Wonder(
    name = "PyramidsGiza",
    title = Res.string.pyramidsGizaTitle,
    subTitle = Res.string.pyramidsGizaSubTitle,
    regionTitle = Res.string.pyramidsGizaRegionTitle,
    historyInfo1 = Res.string.pyramidsGizaHistoryInfo1,
    historyInfo2 = Res.string.pyramidsGizaHistoryInfo2,
    constructionInfo1 = Res.string.pyramidsGizaConstructionInfo1,
    constructionInfo2 = Res.string.pyramidsGizaConstructionInfo2,
    locationInfo1 = Res.string.pyramidsGizaLocationInfo1,
    locationInfo2 = Res.string.pyramidsGizaLocationInfo2,
    pullQuote1Top = Res.string.pyramidsGizaPullQuote1Top,
    pullQuote1Bottom = Res.string.pyramidsGizaPullQuote1Bottom,
    pullQuote1Author = null,
    pullQuote2 = Res.string.pyramidsGizaPullQuote2,
    pullQuote2Author = Res.string.pyramidsGizaPullQuote2Author,
    callout1 = Res.string.pyramidsGizaCallout1,
    callout2 = Res.string.pyramidsGizaCallout2,
    unsplashCollectionId = "CSEvB5Tza9E",
    videoId = "lJKX3Y7Vqvs",
    videoCaption = Res.string.pyramidsGizaVideoCaption,
    mapCaption = Res.string.pyramidsGizaMapCaption,
    startYr = -2600,
    endYr = -2500,
    artifactStartYr = -2800,
    artifactEndYr = -2300,
    artifactCulture = Res.string.pyramidsGizaArtifactCulture,
    artifactGeolocation = Res.string.pyramidsGizaArtifactGeolocation,
    lat = 29.9792,
    lng = 31.1342,
    events = mapOf(
        -2575 to Res.string.pyramidsGiza2575bce,
        -2465 to Res.string.pyramidsGiza2465bce,
        443 to Res.string.pyramidsGiza443bce,
        1925 to Res.string.pyramidsGiza1925ce,
        1979 to Res.string.pyramidsGiza1979ce,
        1990 to Res.string.pyramidsGiza1990ce,
    ),
    searchData = PyramidsGizaSearchData,
    searchSuggestions = PyramidsGizaSearchSuggestions,
)

data object MachuPicchu : Wonder(
    name = "MachuPicchu",
    title = Res.string.machuPicchuTitle,
    subTitle = Res.string.machuPicchuSubTitle,
    regionTitle = Res.string.machuPicchuRegionTitle,
    historyInfo1 = Res.string.machuPicchuHistoryInfo1,
    historyInfo2 = Res.string.machuPicchuHistoryInfo2,
    constructionInfo1 = Res.string.machuPicchuConstructionInfo1,
    constructionInfo2 = Res.string.machuPicchuConstructionInfo2,
    locationInfo1 = Res.string.machuPicchuLocationInfo1,
    locationInfo2 = Res.string.machuPicchuLocationInfo2,
    pullQuote1Top = Res.string.machuPicchuPullQuote1Top,
    pullQuote1Bottom = Res.string.machuPicchuPullQuote1Bottom,
    pullQuote1Author = Res.string.machuPicchuPullQuote1Author,
    pullQuote2 = Res.string.machuPicchuPullQuote2,
    pullQuote2Author = Res.string.machuPicchuPullQuote2Author,
    callout1 = Res.string.machuPicchuCallout1,
    callout2 = Res.string.machuPicchuCallout2,
    unsplashCollectionId = "wUhgZTyUnl8",
    videoId = "cnMa-Sm9H4k",
    videoCaption = Res.string.machuPicchuVideoCaption,
    mapCaption = Res.string.machuPicchuMapCaption,
    startYr = 1450,
    endYr = 1572,
    artifactStartYr = 1200,
    artifactEndYr = 1700,
    artifactCulture = Res.string.machuPicchuArtifactCulture,
    artifactGeolocation = Res.string.machuPicchuArtifactGeolocation,
    lat = -13.162690683637758,
    lng = -72.54500778824891,
    events = mapOf(
        1438 to Res.string.machuPicchu1438ce,
        1572 to Res.string.machuPicchu1572ce,
        1867 to Res.string.machuPicchu1867ce,
        1911 to Res.string.machuPicchu1911ce,
        1964 to Res.string.machuPicchu1964ce,
        1997 to Res.string.machuPicchu1997ce,
    ),
    searchData = MachuPicchuSearchData,
    searchSuggestions = MachuPicchuSearchSuggestions
)

data object GreatWall : Wonder(
    name = "GreatWall",
    title = Res.string.greatWallTitle,
    subTitle = Res.string.greatWallSubTitle,
    regionTitle = Res.string.greatWallRegionTitle,
    historyInfo1 = Res.string.greatWallHistoryInfo1,
    historyInfo2 = Res.string.greatWallHistoryInfo2,
    constructionInfo1 = Res.string.greatWallConstructionInfo1,
    constructionInfo2 = Res.string.greatWallConstructionInfo2,
    locationInfo1 = Res.string.greatWallLocationInfo1,
    locationInfo2 = Res.string.greatWallLocationInfo2,
    pullQuote1Top = Res.string.greatWallPullQuote1Top,
    pullQuote1Bottom = Res.string.greatWallPullQuote1Bottom,
    pullQuote1Author = null,
    pullQuote2 = Res.string.greatWallPullQuote2,
    pullQuote2Author = Res.string.greatWallPullQuote2Author,
    callout1 = Res.string.greatWallCallout1,
    callout2 = Res.string.greatWallCallout2,
    unsplashCollectionId = "Kg_h04xvZEo",
    videoId = "do1Go22Wu8o",
    videoCaption = Res.string.greatWallVideoCaption,
    mapCaption = Res.string.greatWallMapCaption,
    startYr = -700,
    endYr = 1644,
    artifactCulture = Res.string.greatWallArtifactCulture,
    artifactGeolocation = Res.string.greatWallArtifactGeolocation,
    lat = 40.43199751120627,
    lng = 116.57040708482984,
    events = mapOf(
        700 to Res.string.greatWall700bce,
        214 to Res.string.greatWall214bce,
        121 to Res.string.greatWall121bce,
        556 to Res.string.greatWall556ce,
        618 to Res.string.greatWall618ce,
        1487 to Res.string.greatWall1487ce,
    ),
    searchData = GreatWallSearchData,
    searchSuggestions = GreatWallSearchSuggestions
)

data object TajMahal : Wonder(
    name = "TajMahal",
    title = Res.string.tajMahalTitle,
    subTitle = Res.string.tajMahalSubTitle,
    regionTitle = Res.string.tajMahalRegionTitle,
    historyInfo1 = Res.string.tajMahalHistoryInfo1,
    historyInfo2 = Res.string.tajMahalHistoryInfo2,
    constructionInfo1 = Res.string.tajMahalConstructionInfo1,
    constructionInfo2 = Res.string.tajMahalConstructionInfo2,
    locationInfo1 = Res.string.tajMahalLocationInfo1,
    locationInfo2 = Res.string.tajMahalLocationInfo2,
    pullQuote1Top = Res.string.tajMahalPullQuote1Top,
    pullQuote1Bottom = Res.string.tajMahalPullQuote1Bottom,
    pullQuote1Author = Res.string.tajMahalPullQuote1Author,
    pullQuote2 = Res.string.tajMahalPullQuote2,
    pullQuote2Author = Res.string.tajMahalPullQuote2Author,
    callout1 = Res.string.tajMahalCallout1,
    callout2 = Res.string.tajMahalCallout2,
    unsplashCollectionId = "684IRta86_c",
    videoId = "EWkDzLrhpXI",
    videoCaption = Res.string.tajMahalVideoCaption,
    mapCaption = Res.string.tajMahalMapCaption,
    startYr = 1632,
    endYr = 1653,
    artifactCulture = Res.string.tajMahalArtifactCulture,
    artifactGeolocation = Res.string.tajMahalArtifactGeolocation,
    lat = 27.17405039840427,
    lng = 78.04211890065208,
    events = mapOf(
        1631 to Res.string.tajMahal1631ce,
        1647 to Res.string.tajMahal1647ce,
        1658 to Res.string.tajMahal1658ce,
        1901 to Res.string.tajMahal1901ce,
        1984 to Res.string.tajMahal1984ce,
        1998 to Res.string.tajMahal1998ce,
    ),
    searchData = TajMahalSearchData,
    searchSuggestions = TajMahalSearchSuggestions
)