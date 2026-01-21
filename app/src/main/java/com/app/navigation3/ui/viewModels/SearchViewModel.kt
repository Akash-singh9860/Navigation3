package com.app.navigation3.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * UI state for the Search tab.
 *
 * @property searchItems A list of [SearchItemUi] items representing search results or suggestions.
 */
data class SearchTabState(
    val searchItems: List<SearchItemUi> = List(10) {
        SearchItemUi(id = "search_$it")
    }
)

/**
 * UI model representing an individual search result item.
 *
 * @property id The unique identifier for the search item.
 */
data class SearchItemUi(
    val id: String
)

/**
 * ViewModel for the Search tab, responsible for managing search-related data and UI state.
 */
internal class SearchViewModel : ViewModel() {
    private val _state = MutableStateFlow(SearchTabState())

    /**
     * Observable stream of [SearchTabState] for the Search screen.
     */
    val state = _state.asStateFlow()
}
