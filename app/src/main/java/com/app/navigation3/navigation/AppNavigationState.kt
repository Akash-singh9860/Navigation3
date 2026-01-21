package com.app.navigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import com.app.navigation3.ui.screens.HomeTabRoute
import com.app.navigation3.ui.screens.ProfileTabRoute
import com.app.navigation3.ui.screens.SearchTabRoute
import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey as BaseNavKey

/**
 * Sealed interface representing the navigation keys (routes) in the application.
 * Each object implementing this interface defines a unique destination.
 * Using [Serializable] allows these keys to be saved and restored automatically.
 */
@Serializable
sealed interface NavKey : BaseNavKey {
    @Serializable data object Home : NavKey
    @Serializable data object Search : NavKey
    @Serializable data object Profile : NavKey
}

/**
 * A Composable that displays the content associated with the current navigation state.
 * It uses [NavDisplay] to render the appropriate entries based on the [NavigationState].
 *
 * @param navigationState The current state of navigation containing backstacks and current route.
 * @param navigator The navigator used to perform navigation actions within the displayed screens.
 */
@Composable
fun AppNavDisplay(
    navigationState: NavigationState,
    navigator: Navigator
) {
    val appEntryProvider: (NavKey) -> NavEntry<NavKey> = { key ->
        when (key) {
            NavKey.Home -> NavEntry(key) {
                HomeTabRoute(navigator = navigator)
            }
            NavKey.Search -> NavEntry(key) { SearchTabRoute(navigator = navigator) }
            NavKey.Profile -> NavEntry(key) { ProfileTabRoute(navigator = navigator) }
        }
    }
    NavDisplay(
        entries = navigationState.toEntries(appEntryProvider),
        onBack = { navigator.goBack() }
    )
}

/**
 * Creates and remembers a [NavigationState] instance that is saved across process death.
 *
 * @param startRoute The initial route to display when the app starts.
 * @param topLevelRoutes The set of routes that represent top-level destinations (e.g., in a bottom bar or drawer).
 * @return A [NavigationState] that manages the navigation stacks for the application.
 */
@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>
): NavigationState {
    val topLevelRoute = rememberSerializable(
        startRoute, topLevelRoutes,
        serializer = MutableStateSerializer(NavKey.serializer())
    ) {
        mutableStateOf(startRoute)
    }
    val backStacks = topLevelRoutes.associateWith { key ->
        rememberNavBackStack(key)
    }
    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks
        )
    }
}

/**
 * Holds the state of navigation for the application, including backstacks for each top-level route.
 *
 * @property startRoute The initial route of the application.
 * @property topLevelRoute State holding the currently active top-level route.
 * @property backStacks A map of top-level routes to their respective [NavBackStack].
 */
class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<BaseNavKey>>
) {
    /**
     * The currently active top-level route.
     */
    var topLevelRoute: NavKey by topLevelRoute

    /**
     * List of top-level routes that are currently in use, used to determine which stacks to display.
     */
    val stacksInUse: List<NavKey>
        get() = if (topLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            listOf(startRoute, topLevelRoute)
        }
}

/**
 * Extension function to convert [NavigationState] into a list of decorated [NavEntry] that [NavDisplay] can use.
 * This handles state saving for each entry and provides the mapping from [NavKey] to screen content.
 *
 * @param entryProvider A lambda that provides a [NavEntry] for a given [NavKey].
 * @return A list of [NavEntry] representing the current navigation stacks.
 */
@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): SnapshotStateList<NavEntry<BaseNavKey>> {
    val decoratedEntries = backStacks.mapValues { (_, stack) ->
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<BaseNavKey>(),
        )
        val baseProvider: (BaseNavKey) -> NavEntry<BaseNavKey> = { key ->
            if (key is NavKey) {
                @Suppress("UNCHECKED_CAST")
                entryProvider(key) as NavEntry<BaseNavKey>
            } else {
                error("Unknown key type: $key")
            }
        }
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = baseProvider
        )
    }
    return stacksInUse
        .flatMap { decoratedEntries[it] ?: emptyList() }
        .toMutableStateList()
}

/**
 * A helper class to perform navigation actions like [navigate] and [goBack].
 * It interacts with the [NavigationState] to update the current route or the backstack.
 *
 * @property state The [NavigationState] this navigator will manipulate.
 */
class Navigator(val state: NavigationState) {
    /**
     * Navigates to the specified [route].
     * If the route is a top-level route, it switches the current top-level route.
     * Otherwise, it adds the route to the current backstack.
     */
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    /**
     * Navigates back in the current stack.
     * If the current stack has more than one entry, it removes the last one.
     * If the current stack is a top-level route other than the start route, it switches back to the start route.
     */
    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Stack for ${state.topLevelRoute} not found")

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (state.topLevelRoute != state.startRoute) {
            state.topLevelRoute = state.startRoute
        }
    }
}
