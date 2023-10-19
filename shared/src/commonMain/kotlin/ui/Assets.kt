package ui

import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder


object ImagePaths {
    const val root = "images"
    const val common = "images/common"

}


// For wonder specific assets, add an extension property to Wonder for easy lookup
val Wonder.assetPath: String
    get() = when (this) {
        PyramidsGiza -> "${ImagePaths.root}/pyramids"
        GreatWall -> "${ImagePaths.root}/great_wall_of_china"
        Petra -> "${ImagePaths.root}/petra"
        Colosseum -> "${ImagePaths.root}/colosseum"
        ChichenItza -> "${ImagePaths.root}/chichen_itza"
        MachuPicchu -> "${ImagePaths.root}/machu_picchu"
        TajMahal -> "${ImagePaths.root}/taj_mahal"
        ChristRedeemer -> "${ImagePaths.root}/christ_the_redeemer"
    }

fun Wonder.getAssetPath(name: String): String {
    return "$assetPath/$name"
}

val Wonder.mainImageName: String
    get() = when (this) {
        ChichenItza -> "chichen.png"
        ChristRedeemer -> "redeemer.png"
        Colosseum -> "colosseum.png"
        GreatWall -> "great-wall.png"
        MachuPicchu -> "machu-picchu.png"
        Petra -> "petra.png"
        PyramidsGiza -> "pyramids.png"
        TajMahal -> "taj-mahal.png"
    }

val Wonder.homeBtn: String
    get() = "${this.assetPath}/wonder-button.png"

val Wonder.photo1: String
    get() = "${this.assetPath}/photo-1.jpg"

val Wonder.photo2: String
    get() = "${this.assetPath}/photo-2.jpg"

val Wonder.photo3: String
    get() = "${this.assetPath}/photo-3.jpg"

val Wonder.photo4: String
    get() = "${this.assetPath}/photo-4.jpg"

val Wonder.flattened: String
    get() = "${this.assetPath}/flattened.jpg"