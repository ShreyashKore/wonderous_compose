package ui.screens

// import dev.icerock.moko.mvvm.compose.getViewModel
// import dev.icerock.moko.mvvm.compose.viewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import models.Wonder
import ui.composables.BackButton
import ui.theme.Raleway
import ui.theme.TenorSans
import ui.theme.accent1
import ui.theme.black
import ui.theme.white
import utils.prependProxy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtifactListScreen(
    wonder: Wonder,
    onBackClick: () -> Unit,
    onClickArtifact: (artifactId: Int) -> Unit,
) = BoxWithConstraints {
    // Limit max horizontal items to
    val gridItemMinSize = maxOf(180.dp, maxWidth / 6)
    val viewModel = remember(wonder, { ArtifactListViewModel(wonder) })

    Column(
        Modifier.background(color = black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("BROWSE ARTIFACTS", fontSize = 12.sp, color = white, fontFamily = Raleway)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(wonder.title, color = accent1, fontSize = 18.sp, fontFamily = TenorSans)
                }
            },
            navigationIcon = {
                BackButton(onClick = onBackClick)
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
        )

        DockedSearchBar(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            query = viewModel.searchText,
            onQueryChange = { query ->
                viewModel.onChangeQuery(query)
            },
            leadingIcon = {
                Icon(Icons.Rounded.Search, contentDescription = "search")
            },
            placeholder = {
                Text("Search (ex. type or material)")
            },
            shape = RoundedCornerShape(8.dp),
            active = viewModel.searchActive,
            onActiveChange = viewModel::onChangeActive,
            onSearch = { query ->
                viewModel.onTapSuggestion(suggestion = query)
            },
            content = {
                Text(
                    "SUGGESTIONS",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
                HorizontalDivider()
                Column(
                    modifier = Modifier.heightIn(max = 250.dp).verticalScroll(rememberScrollState())
                ) {
                    for (suggestion in viewModel.suggestions) {
                        Text(
                            suggestion,
                            modifier = Modifier.fillMaxWidth()
                                .clickable { viewModel.onTapSuggestion(suggestion) }
                                .padding(horizontal = 12.dp, vertical = 2.dp)
                        )
                    }
                }
            },
        )
        Text(
            "${wonder.searchData.count()} artifacts found",
            color = accent1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

        // Artifacts list
        LazyVerticalStaggeredGrid(
            modifier = Modifier.weight(1f),
            columns = StaggeredGridCells.Adaptive(minSize = gridItemMinSize)
        ) {
            items(viewModel.filteredArtifacts) { artifact ->

                AsyncImage(
                    artifact.imageUrl.prependProxy(),
                    contentDescription = artifact.title,
                    modifier = Modifier.padding(8.dp)
                        .aspectRatio(
                            maxOf(0.5f, artifact.aspectRatio.toFloat())
                        )
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onClickArtifact(artifact.id) },
                    contentScale = ContentScale.FillWidth,
                    onLoading = {
                        // CircularProgressIndicator()
                    }
                )
            }
        }
    }
}