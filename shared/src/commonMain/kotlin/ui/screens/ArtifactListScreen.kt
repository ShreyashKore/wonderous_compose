package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.Wonder

@Composable
fun ArtifactListScreen(
    wonder: Wonder
) {
    Column(
        Modifier.background(color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            IconButton(
                onClick = {},
                Modifier.align(alignment = Alignment.CenterStart)
            ) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "back")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("BROWSE ARTIFACTS", fontSize = 12.sp)
                Text(wonder.title, color = Color.Yellow, fontSize = 14.sp)
            }

        }
        TextField(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            value = "",
            onValueChange = {

            },
            leadingIcon = {
                Icon(Icons.Rounded.Search, contentDescription = "search")
            },
            placeholder = {
                Text("Search (ex. type or material)")
            },
            shape = RoundedCornerShape(6.dp)
        )
        Text("336 artifacts found, 72 in timo")
        LazyVerticalStaggeredGrid(
            modifier = Modifier.weight(1f),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(wonder.searchData.size) { i ->
                val artifact = wonder.searchData[i]
                KamelImage(
                    asyncPainterResource(artifact.imageUrl),
                    contentDescription = artifact.title,
                    modifier = Modifier.padding(8.dp)
                        .aspectRatio(
                            artifact.aspectRatio.toFloat()
                        )
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}