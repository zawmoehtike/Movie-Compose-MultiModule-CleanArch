package com.zawmoehtike.data.network.mappers

import com.zawmoehtike.data.network.responses.MovieDetailCreditsResponse
import com.zawmoehtike.data.network.responses.MovieDetailResponse
import com.zawmoehtike.data.network.ktor.IMAGE_BASE_URL
import com.zawmoehtike.domain.model.MovieDetailVo
import com.zawmoehtike.share.extension.orFalse
import com.zawmoehtike.share.extension.orZero
import javax.inject.Inject

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class MovieDetailVoMapper @Inject constructor(
    private val movieCastVoMapper: MovieCastVoMapper
) {
    fun map(item: MovieDetailResponse?, casts: MovieDetailCreditsResponse?): MovieDetailVo {
        return MovieDetailVo(
            id = item?.id.orZero(),
            adult = item?.adult.orFalse(),
            title = item?.title.orEmpty(),
            backdropPath = IMAGE_BASE_URL + item?.backdropPath.orEmpty(),
            posterPath = IMAGE_BASE_URL + item?.posterPath.orEmpty(),
            runtime = item?.runtime.orZero(),
            releaseDate = item?.releaseDate.orEmpty(),
            voteAverage = item?.voteAverage.orZero(),
            genres = item?.genres?.map {
                MovieDetailVo.Genre(
                    id = it?.id.orZero(),
                    name = it?.name.orEmpty()
                )
            }.orEmpty(),
            originalLanguage = item?.originalLanguage.orEmpty(),
            overview = item?.overview.orEmpty(),
            casts = casts?.cast?.map {
                movieCastVoMapper.map(it)
            }.orEmpty(),
            crews = casts?.crew?.map {
                movieCastVoMapper.map(it)
            }.orEmpty()
        )
    }


}