package com.app.navigation3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.navigation3.navigation.NavKey

/**
 * A custom bottom navigation bar for the application.
 *
 * @param currentNavKey The currently selected [NavKey], used to highlight the active item.
 * @param onNavigate Callback function triggered when a navigation item is selected.
 * @param modifier Modifier to be applied to the navigation bar.
 */
@Composable
fun AppBottomBar(
    currentNavKey: NavKey?,
    onNavigate: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = Color(0xFF1C1C1E),
        contentColor = Color.Gray,
        windowInsets = WindowInsets(0)
    ) {
        drawerItems.forEach { item ->
            val isSelected = currentNavKey == item.route
            val activeColor = Color(0xFF4CAF50)
            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) activeColor.copy(alpha = 0.2f)
                                else Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                        )
                    }
                },
                label = {
                    Text(
                        item.label,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.offset(y = (-10).dp)
                    )
                },
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    selectedTextColor = activeColor,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}
