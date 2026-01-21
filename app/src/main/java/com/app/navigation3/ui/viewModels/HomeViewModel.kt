package com.app.navigation3.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * UI state for the Home tab.
 *
 * @property placeholders A list of [PlaceholderUi] items to be displayed.
 */
data class HomeTabState(
    val placeholders: List<PlaceholderUi> = List(20) {
        PlaceholderUi(id = "ph_$it", isFavorite = false, isInCart = false)
    }
)

/**
 * Represents a placeholder item in the UI.
 *
 * @property id Unique identifier for the item.
 * @property isFavorite Whether the item is marked as favorite.
 * @property isInCart Whether the item is in the cart.
 */
data class PlaceholderUi(
    val id: String,
    val isFavorite: Boolean,
    val isInCart: Boolean,
)

/**
 * ViewModel for the Home tab, managing its UI state and interactions.
 */
internal class HomeTabViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeTabState())
    
    /**
     * Observable stream of [HomeTabState].
     */
    val state = _state.asStateFlow()

    /**
     * Toggles the favorite status of a placeholder item.
     *
     * @param id The ID of the item to toggle.
     */
    fun toggleFavorite(id: String) {
        _state.update { old ->
            old.copy(
                placeholders = old.placeholders.map { ph ->
                    if (ph.id == id) ph.copy(isFavorite = !ph.isFavorite) else ph
                }
            )
        }
    }

}
