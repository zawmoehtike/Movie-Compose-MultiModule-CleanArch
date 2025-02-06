package com.zawmoehtike.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.zawmoehtike.data.cache.AppDatabase
import com.zawmoehtike.data.cache.entities.GenreEntity
import com.zawmoehtike.data.network.services.*
import com.zawmoehtike.data.network.mappers.*
import com.zawmoehtike.data.network.pagingsources.NowPlayingMoviePagingSource
import com.zawmoehtike.data.network.pagingsources.UpComingMoviePagingSource
import com.zawmoehtike.domain.models.*
import com.zawmoehtike.domain.repositories.MovieRepository
import com.zawmoehtike.share.extensions.orZero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by P.T.H.W on 06/04/2024.
 */

private const val ITEMS_PER_PAGE = 10

class MovieRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val service: HomeService,
    private val movieService: MovieService,
    private val movieVoMapper: MovieVoMapper,
    private val movieDetailVoMapper: MovieDetailVoMapper
) : MovieRepository {
    override fun getNowPlayingMovies(): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingMoviePagingSource(service = service) }
        ).flow.map { pagingData ->
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override fun getUpComingMovies(): Flow<PagingData<MovieVo>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UpComingMoviePagingSource(service = service) }
        ).flow.map { pagingData ->
            val genres = database.genreDao().getAllGenres().map {
                GenreVo(it.id, it.name)
            }
            pagingData.map { movieVoMapper.map(it, genres) }
        }
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetailVo {
        val rawDetails = movieService.getMovieDetails(movieId)
        val rawCasts = movieService.getMovieDetailCasts(movieId)
        return movieDetailVoMapper.map(rawDetails, rawCasts)
    }

    override suspend fun getMovieGenres() {
        val raw = movieService.getMovieGenres()
        database.withTransaction {
            database.genreDao().clearGenres()
            database.genreDao().insertGenres(
                raw.genres?.map {
                    GenreEntity(
                        id = it?.id.orZero(),
                        name = it?.name.orEmpty()
                    )
                }.orEmpty()
            )
        }
    }

    override suspend fun getGenreById(id: Int): GenreVo {
        val genreEntity = database.genreDao().getGenreById(id)
        return GenreVo(
            id = genreEntity.id,
            name = genreEntity.name
        )
    }


}