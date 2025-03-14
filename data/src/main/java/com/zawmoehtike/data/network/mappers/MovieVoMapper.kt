package com.zawmoehtike.data.network.mappers

import com.zawmoehtike.data.network.responses.MovieResponse
import com.zawmoehtike.data.network.ktor.IMAGE_BASE_URL
import com.zawmoehtike.domain.models.*
import com.zawmoehtike.share.extensions.orZero
import javax.inject.Inject

/**
 * Created by P.T.H.W on 02/04/2024.
 */
class MovieVoMapper @Inject constructor() {
    fun map(item: MovieResponse?, genres: List<GenreVo>): MovieVo {
        return MovieVo(
            id = item?.id.orZero(),
            title = item?.title.orEmpty(),
            overview = item?.overview.orEmpty(),
            backdropPath = IMAGE_BASE_URL + item?.backdropPath.orEmpty(),
            posterPath = IMAGE_BASE_URL + item?.posterPath.orEmpty(),
            releaseDate = item?.releaseDate.orEmpty(),
            voteAverage = item?.voteAverage.orZero(),
            genreIds = item?.genreIds?.map {
                genres.find { genre -> genre.id == it }?.name.orEmpty()
            }.orEmpty(),
        )
    }
}