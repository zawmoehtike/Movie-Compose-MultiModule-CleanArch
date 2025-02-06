package com.zawmoehtike.domain.repositories

import com.zawmoehtike.domain.models.ActorVo
import com.zawmoehtike.domain.models.MovieVo
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 01/04/2024.
 */
interface HomeRepository {
    suspend fun fetchHomeData()
    fun getDbNowPlayingMovies(): Flow<List<MovieVo>>
    fun getDbUpComingMovies(): Flow<List<MovieVo>>
    fun getDbPopularMovies(): Flow<List<MovieVo>>
    fun getDbPopularPeople(): Flow<List<ActorVo>>
}