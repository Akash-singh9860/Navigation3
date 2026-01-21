package com.app.navigation3.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.navigation3.navigation.NavKey

/**
 * Represents an item in the navigation drawer or bottom bar.
 *
 * @property route The navigation key associated with this item.
 * @property label The display text for the item.
 * @property icon The [ImageVector] to be used as an icon for the item.
 */
data class DrawerItem(
    val route: NavKey,
    val label: String,
    val icon: ImageVector
)

/**
 * List of navigation items used in the application's navigation components.
 */
val drawerItems = listOf(
    DrawerItem(NavKey.Home, "Home", Icons.Default.Home),
    DrawerItem(NavKey.Search, "Search", Icons.Default.Search),
    DrawerItem(NavKey.Profile, "Profile", Icons.Default.Person)
)
