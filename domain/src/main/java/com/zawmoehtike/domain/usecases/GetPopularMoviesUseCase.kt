package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.model.MovieVo
import com.zawmoehtike.domain.repository.HomeRepository
import com.zawmoehtike.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetPopularMoviesUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository
) : FlowUseCase<Unit, List<MovieVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<List<MovieVo>> {
        return homeRepository.getDbPopularMovies()
    }
}