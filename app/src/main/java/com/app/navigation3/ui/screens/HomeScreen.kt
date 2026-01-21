package com.app.navigation3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.navigation3.navigation.Navigator
import com.app.navigation3.ui.viewModels.HomeTabState
import com.app.navigation3.ui.viewModels.HomeTabViewModel
import com.app.navigation3.util.AppColors
import com.app.navigation3.util.shimmerEffect

private val DarkBg = Color(0xFF101010)
private val CardBg = Color(0xFF1C1C1E)
val ActiveIconColor = Color(0xFF4CAF60)
val InactiveIconColor = Color(0xFF636366)

/**
 * Route-level composable for the Home tab.
 *
 * This composable connects the [HomeTabViewModel] to the [HomeTabScreen],
 * managing the state and providing callbacks for user interactions.
 *
 * @param navigator The [Navigator] used for handling navigation events.
 */
@Composable
fun HomeTabRoute(
    navigator: Navigator
) {
    val vm: HomeTabViewModel = viewModel()
    val state by vm.state.collectAsState()

    HomeTabScreen(
        state = state,
        onOpenDetails = { id -> },
        onToggleFavorite = vm::toggleFavorite,
    )
}

/**
 * Main screen for the Home tab.
 *
 * Displays a list of placeholder items in a dark-themed layout.
 *
 * @param state The current UI state containing placeholders.
 * @param onOpenDetails Callback triggered when an item is clicked.
 * @param onToggleFavorite Callback triggered when the favorite button is clicked.
 */
@Composable
fun HomeTabScreen(
    state: HomeTabState,
    onOpenDetails: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
) {
    Scaffold(
        containerColor = DarkBg,
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            items(state.placeholders) { item ->
                DarkListItem(
                    isFavorite = item.isFavorite,
                    onToggleFavorite = { onToggleFavorite(item.id) },
                    onClick = { onOpenDetails(item.id) }
                )
            }
        }
    }
}

/**
 * A styled list item for the dark theme.
 *
 * Features placeholder boxes with shimmer effects and a favorite toggle button.
 *
 * @param isFavorite Whether the item is currently marked as a favorite.
 * @param onToggleFavorite Callback when the favorite icon is tapped.
 * @param onClick Callback when the entire item row is tapped.
 */
@Composable
fun DarkListItem(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect(AppColors.Green950)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .shimmerEffect(AppColors.Green800)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppColors.Green600)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppColors.Green400)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(32.dp)
            ) {
                if (isFavorite) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ActiveIconColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = ActiveIconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = InactiveIconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
