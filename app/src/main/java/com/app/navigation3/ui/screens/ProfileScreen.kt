package com.app.navigation3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.navigation3.navigation.Navigator
import com.app.navigation3.ui.viewModels.MenuItemUi
import com.app.navigation3.ui.viewModels.ProfileViewModel
import com.app.navigation3.util.AppColors
import com.app.navigation3.util.shimmerEffect

private val DarkBg = Color(0xFF101010)
private val CardBg = Color(0xFF1C1C1E)
private val TextSecondary = Color(0xFF8E8E93)
private val DividerColor = Color(0xFF2C2C2E)
private val ActiveGreen = Color(0xFF4CAF60)

/**
 * Route-level composable for the Profile tab.
 *
 * This composable connects the [ProfileViewModel] to the [ProfileTabScreen].
 *
 * @param navigator The [Navigator] used for handling navigation events.
 */
@Composable
fun ProfileTabRoute(
    navigator: Navigator
) {
    val vm: ProfileViewModel = viewModel()
    ProfileTabScreen(vm)
}

/**
 * Main screen for the Profile tab.
 *
 * Displays user profile information, statistics, and menu sections for preferences and security.
 *
 * @param vm The [ProfileViewModel] containing the data for the screen.
 */
@Composable
private fun ProfileTabScreen(
    vm: ProfileViewModel
) {
    Scaffold(
        containerColor = DarkBg,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ProfileHeader()
            StatsRow()
            MenuSection(title = "Preferences", items = vm.preferences)
            MenuSection(title = "Security", items = vm.security)
        }
    }
}

/**
 * Displays the profile header including user name, avatar, and status badges.
 */
@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(CardBg)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2C2C2E)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Akash Singh",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusBadge(text = "Pro", color = Color(0xFF2C2C2E), textColor = Color.White)
                    StatusBadge(text = "Active", color = AppColors.Green950, textColor = ActiveGreen)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50))
            )
        }
    }
}

/**
 * A small badge used for displaying status information.
 *
 * @param text The text to display in the badge.
 * @param color The background color of the badge.
 * @param textColor The color of the text.
 */
@Composable
fun StatusBadge(text: String, color: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

/**
 * Displays a row of three placeholder statistics boxes.
 */
@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .shimmerEffect(AppColors.Teal900)
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(AppColors.Green950)
                    )
                    Column {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect(AppColors.Green800)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect(AppColors.Green600)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays a section of menu items with a title.
 *
 * @param title The title of the section.
 * @param items The list of [MenuItemUi] to display in this section.
 */
@Composable
fun MenuSection(title: String, items: List<MenuItemUi>) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(CardBg)
        ) {
            items.forEachIndexed { index, item ->
                MenuItem(item)
                if (index < items.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 56.dp),
                        thickness = 1.dp,
                        color = DividerColor
                    )
                }
            }
        }
    }
}

/**
 * A single menu item row.
 *
 * @param item The [MenuItemUi] data to display.
 */
@Composable
fun MenuItem(item: MenuItemUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}
