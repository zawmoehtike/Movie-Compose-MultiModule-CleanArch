package com.zawmoehtike.domain.repositories

import androidx.paging.PagingData
import com.zawmoehtike.domain.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 06/04/2024.
 */
interface MovieRepository {
    fun getNowPlayingMovies(): Flow<PagingData<MovieVo>>
    fun getUpComingMovies(): Flow<PagingData<MovieVo>>
    suspend fun getMovieDetails(movieId: String): MovieDetailVo

    // movie genre
    suspend fun getMovieGenres()
    suspend fun getGenreById(id: Int): GenreVo
}