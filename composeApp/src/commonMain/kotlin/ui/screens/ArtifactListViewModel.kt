package ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
// import dev.icerock.moko.mvvm.viewmodel.ViewModel
import models.Wonder

open class ViewModel

class ArtifactListViewModel(wonder: Wonder) : ViewModel() {

    private val allSuggestions = wonder.searchSuggestions
    private val allArtifacts = wonder.searchData

    var suggestions by mutableStateOf(allSuggestions)
        private set
    var searchActive by mutableStateOf(false)
        private set
    var searchText by mutableStateOf("")
        private set
    var filteredArtifacts by mutableStateOf(allArtifacts)
        private set

    fun onChangeActive(isActive: Boolean) {
        searchActive = isActive
    }

    fun onChangeQuery(query: String) {
        searchText = query
        suggestions = if (searchText.trim() == "") {
            allSuggestions
        } else {
            allSuggestions.filter { suggestion ->
                suggestion.contains(searchText.lowercase())
            }
        }
    }

    fun onTapSuggestion(suggestion: String) {
        searchActive = false
        filteredArtifacts = allArtifacts.filter { artifact ->
            artifact.title.lowercase().contains(suggestion) ||
                    artifact.keywords.lowercase().contains(suggestion)
        }
    }
}