package com.zawmoehtike.data.network.services

import com.zawmoehtike.data.network.responses.ActorResponse
import com.zawmoehtike.data.network.responses.MovieResponse
import com.zawmoehtike.data.network.ktor.DataResponse
import com.zawmoehtike.data.network.ktor.ENDPOINT_GET_NOW_PLAYING
import com.zawmoehtike.data.network.ktor.ENDPOINT_GET_UP_COMING
import com.zawmoehtike.data.network.ktor.ENDPOINT_POPULAR_MOVIES
import com.zawmoehtike.data.network.ktor.ENDPOINT_POPULAR_PERSON
import com.zawmoehtike.data.network.ktor.PARAM_PAGE
import com.zawmoehtike.data.network.ktor.toKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Created by P.T.H.W on 01/04/2024.
 */

class HomeService(private val client: HttpClient) {
    suspend fun getNowPlayingMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_GET_NOW_PLAYING.toKtor()
        return client.get(endpoint) {
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getUpComingMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_GET_UP_COMING.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getPopularMovies(page: Int = 1): DataResponse<List<MovieResponse>> {
        val endpoint = ENDPOINT_POPULAR_MOVIES.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }

    suspend fun getPopularPeople(page: Int = 1): DataResponse<List<ActorResponse>> {
        val endpoint = ENDPOINT_POPULAR_PERSON.toKtor()
        return client.get(endpoint){
            parameter(PARAM_PAGE, page)
        }.body()
    }
}