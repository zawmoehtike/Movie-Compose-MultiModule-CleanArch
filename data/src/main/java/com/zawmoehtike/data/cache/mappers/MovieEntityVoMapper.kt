package com.zawmoehtike.data.cache.mappers

import com.zawmoehtike.data.cache.entities.*
import com.zawmoehtike.domain.model.*
import com.zawmoehtike.share.mapper.*
import javax.inject.Inject

/**
 * Created by P.T.H.W on 03/04/2024.
 */
class MovieEntityVoMapper @Inject constructor() : UnidirectionalMap<MovieEntity, MovieVo> {
    override fun map(item: MovieEntity): MovieVo {
        return MovieVo(
            id = item.id,
            title = item.title,
            overview = item.overview,
            backdropPath = item.backdropPath,
            releaseDate = item.releaseDate,
            posterPath = item.posterPath,
            voteAverage = item.voteAverage,
            genreIds = item.genreIds,
            isFavorite = item.isFavorite
        )
    }
}