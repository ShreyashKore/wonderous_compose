package ui.theme

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder

val Wonder.bgColor: Color
    get() = when (this) {
        PyramidsGiza -> Color(0xFF16184D)
        GreatWall -> Color(0xFF642828)
        Petra -> Color(0xFF444B9B)
        Colosseum -> Color(0xFF1E736D)
        ChichenItza -> Color(0xFF164F2A)
        MachuPicchu -> Color(0xFF0E4064)
        TajMahal -> Color(0xFFC96454)
        ChristRedeemer -> Color(0xFF1C4D46)
    }

val Wonder.fgColor: Color
    get() = when (this) {
        PyramidsGiza -> Color(0xFF444B9B)
        GreatWall -> Color(0xFF688750)
        Petra -> Color(0xFF1B1A65)
        Colosseum -> Color(0xFF4AA39D)
        ChichenItza -> Color(0xFFE2CFBB)
        MachuPicchu -> Color(0xFFC1D9D1)
        TajMahal -> Color(0xFF642828)
        ChristRedeemer -> Color(0xFFED7967)
    }

val Color.colorFilter: ColorFilter
    get() = ColorFilter.tint(this, BlendMode.SrcIn)
