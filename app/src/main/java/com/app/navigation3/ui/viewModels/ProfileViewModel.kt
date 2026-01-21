package com.app.navigation3.ui.viewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel

/**
 * UI model representing a menu item in the profile screen.
 *
 * @property icon The icon to display for the menu item.
 * @property title The main text label for the menu item.
 * @property subtitle A descriptive subtitle for the menu item.
 */
data class MenuItemUi(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

/**
 * ViewModel for the Profile tab, providing data for settings and security menus.
 */
internal class ProfileViewModel : ViewModel() {
    /**
     * List of user preference menu items.
     */
    val preferences = listOf(
        MenuItemUi(Icons.Default.Settings, "Settings", "Personalize your app"),
        MenuItemUi(Icons.Default.Notifications, "Notifications", "Sounds, alerts, quiet hours")
    )

    /**
     * List of security-related menu items.
     */
    val security = listOf(
        MenuItemUi(Icons.Default.PrivacyTip, "Privacy", "Permissions, device access"),
        MenuItemUi(Icons.Default.CreditCard,"Payments", "Cards, billing, receipts")
    )
}
