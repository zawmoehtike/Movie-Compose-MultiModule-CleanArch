package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.listing

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmoehtike.domain.models.MovieVo
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.ErrorMessage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.LoadingNextPageItem
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.PageLoader
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.SharedAnimatedContent
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.TopAppBarView
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.listing.composable.MovieGridItemView
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.moviedetails.navigateToMovieDetailPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.Dimens
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.LocalNavController
import kotlinx.coroutines.flow.flowOf


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieListingPage(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navController: NavController = LocalNavController.current,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    PageContent(
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        pagingItems = viewModel.nowPlayingPagingFlow.collectAsLazyPagingItems(),
        onAction = {
            when (it) {
                is UiEvent.GoBack -> navController.popBackStack()
                is UiEvent.ItemClick -> navController.navigateToMovieDetailPage(
                    movieId = it.movie.id, backdropPath = it.movie.backdropPath
                )
            }
        }
    )
}

private sealed class UiEvent {
    data object GoBack : UiEvent()
    class ItemClick(val movie: MovieVo) : UiEvent()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PageContent(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    pagingItems: LazyPagingItems<MovieVo>,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarView(title = "Movies") {
                onAction(UiEvent.GoBack)
            }
        },
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                start = Dimens.MARGIN_MEDIUM_2,
                end = Dimens.MARGIN_MEDIUM_2,
                top = Dimens.MARGIN_MEDIUM,
                bottom = Dimens.MARGIN_LARGE
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.MARGIN_20),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MARGIN_MEDIUM_2)
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let {
                    MovieGridItemView(
                        modifier = modifier,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        movieVo = it
                    ) {
                        onAction(UiEvent.ItemClick(it))
                    }
                }
            }
            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            PageLoader(modifier = Modifier)
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(2) }) {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            LoadingNextPageItem(modifier = Modifier)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        item(span = { GridItemSpan(2) }) {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedAnimatedContent {
            PageContent(
                modifier = Modifier,
                sharedTransitionScope = this,
                animatedContentScope = it,
                pagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems()
            )
        }
    }
}