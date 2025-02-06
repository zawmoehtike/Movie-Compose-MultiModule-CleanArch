package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.models.MovieVo
import com.zawmoehtike.domain.repositories.HomeRepository
import com.zawmoehtike.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetUpComingMoviesUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository
) : FlowUseCase<Unit, List<MovieVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<List<MovieVo>> {
        return homeRepository.getDbUpComingMovies()
    }

}