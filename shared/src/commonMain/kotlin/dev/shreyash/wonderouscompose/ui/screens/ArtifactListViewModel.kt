package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import models.Wonder


class ArtifactListViewModel(wonder: Wonder) : ViewModel() {

    private val allSuggestions = wonder.searchSuggestions
    private val allArtifacts = wonder.searchData

    val searchText = MutableStateFlow("")

    val suggestions = searchText.map {
        if (it.isBlank()) allSuggestions
        else allSuggestions.filter { suggestion ->
            suggestion.contains(it.lowercase())
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        allSuggestions
    )


    val filteredArtifacts = MutableStateFlow(allArtifacts)

    fun onQueryChange(query: String) = searchText.update { query }

    fun onSearch(suggestion: String) {
        filteredArtifacts.update {
            allArtifacts.filter { artifact ->
                artifact.title.lowercase().contains(suggestion) ||
                        artifact.keywords.lowercase().contains(suggestion)
            }
        }
    }
}