package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.repository.MovieRepository
import com.zawmoehtike.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 26/04/2024.
 */
class GetMovieGenresUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val movieRepository: MovieRepository
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider) {
    override suspend fun provide(params: Unit) {
        movieRepository.getMovieGenres()
    }
}