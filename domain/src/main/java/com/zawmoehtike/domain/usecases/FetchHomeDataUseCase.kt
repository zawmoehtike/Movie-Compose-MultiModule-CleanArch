package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.repository.HomeRepository
import com.zawmoehtike.domain.utils.CoroutineUseCase
import javax.inject.Inject

/**
 * Created by P.T.H.W on 20/04/2024.
 */
class FetchHomeDataUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository,
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider) {
    override suspend fun provide(params: Unit) {
        homeRepository.fetchHomeData()
    }
}