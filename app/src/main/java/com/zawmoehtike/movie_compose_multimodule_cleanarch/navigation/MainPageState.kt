package com.zawmoehtike.movie_compose_multimodule_cleanarch.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.home.navigateToHomeNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.movie.navigateToMovieNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.profile.navigateToProfileNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.ticket.navigateToTicketNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.LocalNavController
import kotlinx.coroutines.CoroutineScope

/**
 * Created by P.T.H.W on 13/11/2023.
 */


@Composable
fun rememberMainPageState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = LocalNavController.current,
): MainPageState {
    return remember(navController, coroutineScope, windowSizeClass) {
        MainPageState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
        )
    }
}

class MainPageState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
//            forYouNavigationRoute -> TopLevelDestination.FOR_YOU
//            bookmarksRoute -> BOOKMARKS
//            interestsRoute -> INTERESTS
            else -> null
        }

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHomeNavPage(topLevelNavOptions)
                TopLevelDestination.TICKET -> navController.navigateToTicketNavPage(
                    topLevelNavOptions
                )

                TopLevelDestination.MOVIE -> navController.navigateToMovieNavPage(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfileNavPage(
                    topLevelNavOptions
                )
            }
        }
    }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries
}