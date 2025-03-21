package com.zawmoehtike.movie_compose_multimodule_cleanarch.features.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.pullrefresh.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zawmoehtike.base.viewstates.ObjViewState
import com.zawmoehtike.base.viewstates.RenderCompose
import com.zawmoehtike.base.viewstates.renderState
import com.zawmoehtike.domain.models.*
import com.zawmoehtike.movie_compose_multimodule_cleanarch.R
import com.zawmoehtike.movie_compose_multimodule_cleanarch.composable.*
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.listing.movieListingPageNavigationRoute
import com.zawmoehtike.movie_compose_multimodule_cleanarch.features.moviedetails.navigateToMovieDetailPage
import com.zawmoehtike.movie_compose_multimodule_cleanarch.ui.theme.*
import com.zawmoehtike.share.extensions.*
import timber.log.Timber
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("ResourceAsColor")
@Composable
fun HomeNavPage(
    modifier: Modifier = Modifier,
    viewModel: HomeNavPageViewModel = hiltViewModel(),
    navController: NavController = LocalNavController.current,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    Timber.i("OnReached: HomeNavPage")

    val uiState = UiState(
        refreshing = viewModel.refreshing.value,
        nowPlayingMovies = viewModel.nowPlayingMovies.value,
        comingSoonMovies = viewModel.upComingMovies.value,
        popularMovies = viewModel.popularMovies.value,
        popularActors = viewModel.popularPeople.value
    )

    fun onAction(action: UiEvent) {
        when (action) {
            UiEvent.Refresh -> viewModel.refreshHomeData()
            UiEvent.SeeAll -> navController.navigate(movieListingPageNavigationRoute)
            is UiEvent.ItemClick -> {
                navController.navigateToMovieDetailPage(
                    movieId = action.movie.id, backdropPath = action.movie.backdropPath
                )
            }
        }
    }

    if (uiState.isReady()) {
        HomePageContent(
            modifier = modifier,
            uiState = uiState,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            onAction = {
                onAction(it)
            },
        )
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

}

private data class UiState(
    val refreshing: Boolean = false,
    val nowPlayingMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val comingSoonMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val popularMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val popularActors: ObjViewState<List<ActorVo>> = ObjViewState.Idle()
) {
    fun isReady() =
        nowPlayingMovies is ObjViewState.Success && comingSoonMovies is ObjViewState.Success
                && popularMovies is ObjViewState.Success && popularActors is ObjViewState.Success
}

private sealed class UiEvent {
    data object Refresh : UiEvent()
    data object SeeAll : UiEvent()
    class ItemClick(val movie: MovieVo) : UiEvent()
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
private fun HomePageContent(
    modifier: Modifier,
    uiState: UiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onAction: (UiEvent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2, vertical = Dimens.MARGIN_MEDIUM)
            ) {
                Column(modifier.weight(1f)) {
                    Text(
                        "Hi, Angelina \uD83D\uDC4B",
                        fontSize = Dimens.TEXT_REGULAR
                    )
                    Text(
                        text = "Welcome back",
                        fontSize = Dimens.TEXT_XLARGE,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
    ) { innerPadding ->
        val promoPagerState = rememberPagerState { 20 }
        val state = rememberPullRefreshState(uiState.refreshing, { onAction(UiEvent.Refresh) })

        Box(Modifier.pullRefresh(state)) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                item {

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

                    HomeSearchBarView(modifier = modifier)

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Now Playing
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = "Now playing"
                    ) {
                        onAction(UiEvent.SeeAll)
                    }

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    uiState.nowPlayingMovies.renderState(
                        loading = {
                            Log.d("ZawVenom", "Loading...")
                        },
                        success = {
                            Log.d("ZawVenom", "Success!")
                        }
                    )

                    RenderCompose(uiState.nowPlayingMovies,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            NowPlayingMoviesSectionView(
                                modifier = modifier,
                                movies = it.take(8)
                            ) {
                                onAction(UiEvent.ItemClick(it))
                            }
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Coming Soon
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = "Coming soon"
                    )

                    RenderCompose(uiState.comingSoonMovies,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                            ) {
                                items(it.size) { index ->
                                    ComingSoonMoviesItemView(
                                        modifier, it[index],
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope
                                    ) {
                                        onAction(UiEvent.ItemClick(it))
                                    }
                                }
                            }
                        }
                    )

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))


                    // Promo & Discount
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = "Promo & Discount"
                    )

                    RenderCompose(uiState.popularMovies,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            if (it.isEmpty()) return@RenderCompose
                            HorizontalPager(
                                state = promoPagerState,
                                modifier = modifier
                                    .heightIn(max = (LocalConfiguration.current.screenHeightDp / 4).dp),
                                pageSpacing = Dimens.MARGIN_MEDIUM_2,
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                            ) { index ->
                                CoilAsyncImage(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .clip(Shapes.small)
                                        .simpleClickable {
                                            onAction(UiEvent.ItemClick(it[index]))
                                        },
                                    imageUrl = it[index].backdropPath,
                                )
                            }
                        }
                    )


                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Service
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = "Celebrities"
                    )

                    RenderCompose(uiState.popularActors,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                            ) {
                                items(it.size) { index ->
                                    CelebritiesItemView(modifier = modifier, actorVo = it[index])
                                }
                            }
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Movie News
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = "Movie News"
                    )


                    RenderCompose(uiState.nowPlayingMovies,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                            ) {
                                items(it.size) { index ->
                                    Column(
                                        modifier = modifier
                                            .widthIn(max = (LocalConfiguration.current.screenHeightDp / 3).dp)
                                            .padding(end = Dimens.MARGIN_MEDIUM_2)
                                    ) {
                                        CoilAsyncImage(
                                            modifier = modifier
                                                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 5).dp)
                                                .clip(Shapes.small),
                                            imageUrl = it[index].backdropPath,
                                        )
                                        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
                                        Text(
                                            text = buildAnnotatedString {
                                                if (it[index].overview.length > 100) {
                                                    append(it[index].overview.substring(0..100))
                                                    append("... ")
                                                    withStyle(style = SpanStyle(color = ColorPrimary)) {
                                                        append("See more")
                                                    }
                                                } else {
                                                    append(it[index].overview)
                                                }
                                            },
                                            minLines = 5,
                                            fontSize = Dimens.TEXT_SMALL,
                                        )
                                    }
                                }
                            }
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_LARGE))

                }
            }

            PullRefreshIndicator(
                uiState.refreshing,
                state,
                Modifier
                    .padding(innerPadding)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NowPlayingMoviesSectionView(
    modifier: Modifier,
    movies: List<MovieVo>,
    itemClick: (movie: MovieVo) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        movies.size
    })
    Column {
        HorizontalPager(
            modifier = modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2.8).dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 80.dp,
                vertical = 8.dp
            ),
            state = pagerState
        ) { page ->
            HorizontalPagerItemView(
                modifier = modifier.clickable {
                    itemClick(movies[page])
                },
                pagerState = pagerState,
                currentPage = page,
                movieVo = movies[page]
            )
        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        TitleTextView(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XXXLARGE),
            text = movies[pagerState.currentPage].title,
            maxLines = 1
        )

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_SMALL))

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XLARGE),
            textAlign = TextAlign.Center,
            text = "${movies[pagerState.currentPage].releaseDate} • ${
                movies[pagerState.currentPage].genreIds.joinToString(
                    ", "
                )
            }",
            fontSize = Dimens.TEXT_REGULAR_2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Star, "", tint = ColorPrimary)
                TitleTextView(
                    modifier = modifier,
                    text = movies[pagerState.currentPage].voteAverage.roundTo(1).toString()
                )
            }

            Text(
                "(1.222)",
                fontSize = Dimens.TEXT_XSMALL,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        HorizontalPagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )
    }

}

@Composable
private fun CelebritiesItemView(
    modifier: Modifier,
    actorVo: ActorVo?
) {
    Column(
        modifier = modifier
            .padding(end = Dimens.MARGIN_MEDIUM_2)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilAsyncImage(
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape),
            imageUrl = actorVo?.profilePath.toString(),
        )
        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
        Text(
            text = actorVo?.name.orEmpty(),
            fontSize = Dimens.TEXT_REGULAR_2,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerItemView(
    modifier: Modifier,
    pagerState: PagerState,
    currentPage: Int,
    movieVo: MovieVo
) {
    val pageOffset = pagerState.currentPage - currentPage + pagerState.currentPageOffsetFraction
    Card(
        modifier = modifier
            .graphicsLayer {
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                )

                cameraDistance = 8 * density
                rotationY = lerp(
                    start = 0f,
                    stop = 0f,
                    fraction = pageOffset.coerceIn(-1f, 1f),
                )

                lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue + 0.2f,
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },

        content = {
            CoilAsyncImage(
                modifier = modifier.fillMaxSize(),
                imageUrl = movieVo.posterPath,
            )
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ComingSoonMoviesItemView(
    modifier: Modifier,
    movieVo: MovieVo,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    itemClick: (movie: MovieVo) -> Unit
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .width(180.dp)
                .padding(end = Dimens.MARGIN_MEDIUM_2)
                .sharedElement(
                    state = rememberSharedContentState(
                        key = "image-${movieVo.id}"
                    ),
                    animatedVisibilityScope = animatedContentScope,
                )
                .simpleClickable {
                    itemClick(movieVo)
                }
        ) {
            CoilAsyncImage(
                imageUrl = movieVo.posterPath,
                modifier = modifier
                    .height(220.dp)
                    .width(180.dp)
                    .clip(Shapes.small)
            )

            Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

            Text(
                text = movieVo.title,
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = movieVo.genreIds.joinToString(", "), fontSize = Dimens.TEXT_SMALL,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar), "")
                Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                Text(text = movieVo.releaseDate, fontSize = Dimens.TEXT_SMALL)
            }
        }
    }
}

@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = ColorPrimary,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 12.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    Color.DarkGray to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}

@Composable
private fun HomeSearchBarView(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
            .clip(Shapes.medium)
            .background(color = LocalCustomColors.current.searchBoxColor)
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_normal),
            contentDescription = ""
        )
        CustomTextField(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(start = Dimens.MARGIN_MEDIUM),
            placeholderText = "Search"
        )
    }

}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeNavPageNightPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true, label = ""
            ) {
                Surface {
                    HomePageContent(
                        modifier = Modifier,
                        uiState = UiState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeNavPagePreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true, label = ""
            ) {
                Surface {
                    HomePageContent(
                        modifier = Modifier,
                        uiState = UiState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun SectionTitleWithSeeAllPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            SectionTitleWithSeeAll(modifier = Modifier, title = "Promo & Test")
        }
    }
}

@Preview
@Composable
private fun CelebritiesItemViewPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            CelebritiesItemView(modifier = Modifier, null)
        }
    }
}

@Composable
fun <T> InternalRenderCompose(
    state: ObjViewState<T>,
    idle: @Composable () -> Unit = {},
    loading: @Composable () -> Unit = {},
    success: @Composable (data: T) -> Unit,
    error: @Composable (msg: String) -> Unit = {},
) {
    when (state) {
        is ObjViewState.Loading -> {
            loading.invoke()
        }

        is ObjViewState.Success -> {
            success.invoke(state.value)
        }

        is ObjViewState.Error -> {
            error.invoke(state.errorMessage)
        }

        else -> idle.invoke()
    }
}