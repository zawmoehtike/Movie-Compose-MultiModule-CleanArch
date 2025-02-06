package com.zawmoehtike.data.network.services

import com.zawmoehtike.data.network.responses.GenresResponse
import com.zawmoehtike.data.network.responses.MovieDetailCreditsResponse
import com.zawmoehtike.data.network.responses.MovieDetailResponse
import com.zawmoehtike.data.network.ktor.ENDPOINT_MOVIE_DETAIL
import com.zawmoehtike.data.network.ktor.ENDPOINT_MOVIE_GENRES
import com.zawmoehtike.data.network.ktor.toKtor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

/**
 * Created by P.T.H.W on 24/04/2024.
 */
class MovieService(private val client: HttpClient) {
    suspend fun getMovieDetails(movieId: String): MovieDetailResponse {
        val endpoint = ENDPOINT_MOVIE_DETAIL.toKtor()
        return client.get(endpoint) {
            url {
                appendPathSegments(movieId)
            }
        }.body()
    }

    suspend fun getMovieDetailCasts(movieId: String): MovieDetailCreditsResponse {
        val endpoint = ENDPOINT_MOVIE_DETAIL.toKtor()
        return client.get(endpoint) {
            url {
                appendPathSegments(movieId, "credits")
            }
        }.body()
    }

    suspend fun getMovieGenres(): GenresResponse {
        val endpoint = ENDPOINT_MOVIE_GENRES.toKtor()
        return client.get(endpoint).body()
    }
}