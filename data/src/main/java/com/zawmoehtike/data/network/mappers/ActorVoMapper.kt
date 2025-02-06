package com.zawmoehtike.data.network.mappers

import com.zawmoehtike.data.network.ktor.IMAGE_BASE_URL
import com.zawmoehtike.data.network.responses.ActorResponse
import com.zawmoehtike.domain.model.ActorVo
import com.zawmoehtike.share.extension.orFalse
import com.zawmoehtike.share.extension.orZero
import com.zawmoehtike.share.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class ActorVoMapper @Inject constructor() : UnidirectionalMap<ActorResponse?, ActorVo> {
    override fun map(item: ActorResponse?): ActorVo {
        return ActorVo(
            adult = item?.adult.orFalse(),
            gender = item?.gender.orZero(),
            id = item?.id.orZero(),
            knownForDepartment = item?.knownForDepartment.orEmpty(),
            name = item?.name.orEmpty(),
            originalName = item?.originalName.orEmpty(),
            popularity = item?.popularity.orZero(),
            profilePath = IMAGE_BASE_URL + item?.profilePath.orEmpty()
        )
    }
}