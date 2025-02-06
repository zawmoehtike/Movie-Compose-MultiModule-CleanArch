package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.model.MovieDetailVo
import com.zawmoehtike.domain.repository.MovieRepository
import com.zawmoehtike.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class GetMovieDetailUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<String, MovieDetailVo>(dispatcherProvider) {
    override suspend fun provide(params: String): MovieDetailVo {
        return movieRepository.getMovieDetails(params)
    }
}