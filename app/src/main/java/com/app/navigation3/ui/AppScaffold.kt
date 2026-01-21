package com.app.navigation3.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.DelegatableNode
import com.app.navigation3.navigation.AppNavDisplay
import com.app.navigation3.navigation.NavKey
import com.app.navigation3.navigation.Navigator
import com.app.navigation3.navigation.rememberNavigationState
import com.app.navigation3.ui.components.AppBottomBar
import kotlinx.coroutines.launch

/**
 * The main scaffold of the application, responsible for setting up the top-level UI structure.
 *
 * This composable:
 * - Disables the default ripple effect using [NoRipple].
 * - Initializes and remembers the navigation state and navigator.
 * - Handles the system back button logic, either closing the navigation drawer
 *   or navigating back in the stack.
 * - Displays an [AppBottomBar] for switching between top-level destinations.
 * - Hosts the [AppNavDisplay] to render the current destination's content.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    CompositionLocalProvider(LocalIndication provides NoRipple) {
        val topLevelRoutes = remember { setOf(NavKey.Home, NavKey.Search, NavKey.Profile) }
        val navState = rememberNavigationState(
            startRoute = NavKey.Home,
            topLevelRoutes = topLevelRoutes
        )
        val navigator = remember(navState) { Navigator(navState) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        BackHandler(enabled = true) {
            if (drawerState.isOpen) {
                scope.launch { drawerState.close() }
            } else {
                navigator.goBack()
            }
        }
        val currentNavKey = navState.topLevelRoute
            Scaffold(
                containerColor = Color(0xFF101010),
                bottomBar = {
                    AppBottomBar(
                        currentNavKey = currentNavKey,
                        onNavigate = { destination ->
                            navigator.navigate(destination)
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavDisplay(
                        navigationState = navState,
                        navigator = navigator
                    )
                }
            }

    }
}

/**
 * An [IndicationNodeFactory] that renders no indication (like ripples) for interactions.
 * Used to provide a clean, non-interactive visual style where desired.
 */
private object NoRipple : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return object : DelegatableNode, Modifier.Node() {}
    }
    override fun equals(other: Any?) = other === NoRipple
    override fun hashCode() = 100
}
