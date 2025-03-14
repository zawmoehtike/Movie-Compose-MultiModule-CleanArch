package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.ticket

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

/**
 * Created by P.T.H.W on 19/04/2024.
 */

const val invoiceTicketPageNavigationRoute = "invoice-ticket"
fun NavGraphBuilder.invoiceTicketPage() {
    composable(
        route = invoiceTicketPageNavigationRoute,
    ) {
        InvoiceTicketPage()
    }
}

fun NavController.navigateToInvoiceTicketPage(navOptions: NavOptions? = null) =
    navigate(invoiceTicketPageNavigationRoute, navOptions)
