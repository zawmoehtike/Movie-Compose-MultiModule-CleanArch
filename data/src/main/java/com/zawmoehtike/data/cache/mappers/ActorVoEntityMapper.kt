package com.zawmoehtike.data.cache.mappers

import com.zawmoehtike.data.cache.entities.*
import com.zawmoehtike.data.network.ktor.*
import com.zawmoehtike.domain.model.*
import com.zawmoehtike.share.mapper.*
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class ActorVoEntityMapper @Inject constructor() : UnidirectionalMap<ActorVo, ActorEntity> {
    override fun map(item: ActorVo): ActorEntity {
        return ActorEntity(
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