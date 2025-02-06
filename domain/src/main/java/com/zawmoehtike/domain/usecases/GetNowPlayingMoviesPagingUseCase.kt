package com.zawmoehtike.domain.usecases

import androidx.paging.PagingData
import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.model.MovieVo
import com.zawmoehtike.domain.repository.MovieRepository
import com.zawmoehtike.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */
class GetNowPlayingMoviesPagingUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : FlowUseCase<Unit, PagingData<MovieVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<PagingData<MovieVo>> {
        return movieRepository.getNowPlayingMovies()
    }
}