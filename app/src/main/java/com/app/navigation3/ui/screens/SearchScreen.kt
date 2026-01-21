package com.app.navigation3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.navigation3.navigation.Navigator
import com.app.navigation3.ui.viewModels.SearchTabState
import com.app.navigation3.ui.viewModels.SearchViewModel
import com.app.navigation3.util.AppColors
import com.app.navigation3.util.shimmerEffect

private val DarkBg = Color(0xFF101010)
private val CardBg = Color(0xFF1C1C1E)
private val InactiveIconColorGrey = Color(0xFF636366)

/**
 * Route-level composable for the Search tab.
 *
 * Connects the [SearchViewModel] to the [SearchTabScreen].
 *
 * @param navigator The [Navigator] used for handling navigation events.
 */
@Composable
fun SearchTabRoute(
    navigator: Navigator
) {
    val vm: SearchViewModel = viewModel()
    val state by vm.state.collectAsState()

    SearchTabScreen(
        state = state,
        onItemClick = {  }
    )
}

/**
 * Main screen for the Search tab.
 *
 * Displays a search bar skeleton and a grid of search results.
 *
 * @param state The current UI state for the search tab.
 * @param onItemClick Callback triggered when a search result item is clicked.
 */
@Composable
fun SearchTabScreen(
    state: SearchTabState,
    onItemClick: (String) -> Unit
) {
    Scaffold(
        containerColor = DarkBg,
        topBar = {
            SearchBarSkeleton()
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(state.searchItems) { item ->
                SearchGridItem(
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}

/**
 * A skeleton placeholder for the search bar.
 */
@Composable
fun SearchBarSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .statusBarsPadding()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(CardBg)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = InactiveIconColorGrey,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmerEffect(AppColors.Green950)
            )
        }
    }
}

/**
 * A styled grid item for search results.
 *
 * @param onClick Callback when the item is tapped.
 */
@Composable
fun SearchGridItem(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(20.dp))
            .background(CardBg)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect(AppColors.Green950)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect(AppColors.Green800)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .shimmerEffect(AppColors.Green600)
                )
            }
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .shimmerEffect(AppColors.Green300)
            )
        }
    }
}

/**
 * Composites this color over the specified [background] color.
 *
 * @param background The background color to composite over.
 * @return The resulting [Color].
 */
fun Color.compositeOver(background: Color): Color {
    val alpha = this.alpha
    val oneMinusAlpha = 1f - alpha
    val r = this.red * alpha + background.red * oneMinusAlpha
    val g = this.green * alpha + background.green * oneMinusAlpha
    val b = this.blue * alpha + background.blue * oneMinusAlpha
    return Color(r, g, b, 1f)
}
