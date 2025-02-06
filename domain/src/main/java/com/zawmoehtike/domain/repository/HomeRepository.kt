package com.zawmoehtike.domain.repository

import com.zawmoehtike.domain.model.ActorVo
import com.zawmoehtike.domain.model.MovieVo
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