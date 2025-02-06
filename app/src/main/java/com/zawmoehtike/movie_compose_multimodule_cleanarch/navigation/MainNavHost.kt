package com.zawmoehtike.movie_compose_multimodule_cleanarch.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.cinemaseat.cinemaSeatPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.home.homeNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.home.homeNavPageNavigationRoute
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.listing.movieListingPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.movie.movieNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.moviedetails.movieDetailPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.payment.paymentPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.profile.profileNavPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.ticket.invoiceTicketPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.ticket.ticketNavPage

/**
 * Created by P.T.H.W on 25/03/2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavHost(
    modifier: Modifier,
    appState: MainPageState,
    startDestination: String = homeNavPageNavigationRoute
) {

    SharedTransitionLayout {
        NavHost(
            navController = appState.navController,
            startDestination = startDestination,
            modifier = modifier,
        ) {
            homeNavPage(this@SharedTransitionLayout)
            ticketNavPage()
            movieNavPage(this@SharedTransitionLayout)
            profileNavPage()
            movieListingPage(this@SharedTransitionLayout)
            movieDetailPage(this@SharedTransitionLayout)
            cinemaSeatPage()
            paymentPage()
            invoiceTicketPage()
        }
    }
}