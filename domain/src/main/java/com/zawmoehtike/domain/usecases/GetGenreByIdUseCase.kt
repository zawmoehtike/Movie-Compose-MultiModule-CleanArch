package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.model.GenreVo
import com.zawmoehtike.domain.repository.MovieRepository
import com.zawmoehtike.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetGenreByIdUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<Int, GenreVo>(dispatcherProvider) {
    override suspend fun provide(params: Int): GenreVo {
        return movieRepository.getGenreById(params)
    }
}