package com.zawmoehtike.domain.usecases

import com.zawmoehtike.domain.DispatcherProvider
import com.zawmoehtike.domain.models.ActorVo
import com.zawmoehtike.domain.repositories.HomeRepository
import com.zawmoehtike.domain.utils.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class GetPopularPeopleUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val homeRepository: HomeRepository
) : FlowUseCase<Unit, List<ActorVo>>(dispatcherProvider) {
    override fun provide(params: Unit): Flow<List<ActorVo>> {
        return homeRepository.getDbPopularPeople()
    }

}