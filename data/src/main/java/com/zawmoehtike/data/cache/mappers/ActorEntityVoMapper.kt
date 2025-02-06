package com.zawmoehtike.data.cache.mappers

import com.zawmoehtike.data.cache.entities.ActorEntity
import com.zawmoehtike.data.network.ktor.IMAGE_BASE_URL
import com.zawmoehtike.domain.model.ActorVo
import com.zawmoehtike.share.mapper.UnidirectionalMap
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class ActorEntityVoMapper @Inject constructor() : UnidirectionalMap<ActorEntity, ActorVo> {
    override fun map(item: ActorEntity): ActorVo {
        return ActorVo(
            adult = item.adult,
            gender = item.gender,
            id = item.id,
            knownForDepartment = item.knownForDepartment,
            name = item.name,
            originalName = item.originalName,
            popularity = item.popularity,
            profilePath = IMAGE_BASE_URL + item.profilePath
        )
    }
}