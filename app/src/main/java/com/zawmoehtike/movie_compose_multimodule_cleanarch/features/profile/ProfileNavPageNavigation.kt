package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 27/03/2024.
 */

const val profileNavPageNavigationRoute = "PROFILE"
fun NavGraphBuilder.profileNavPage() {
    composable(
        route = profileNavPageNavigationRoute,
    ) {
        ProfileNavPage()
    }
}

fun NavController.navigateToProfileNavPage(navOptions: NavOptions? = null) =
    navigate(profileNavPageNavigationRoute, navOptions)