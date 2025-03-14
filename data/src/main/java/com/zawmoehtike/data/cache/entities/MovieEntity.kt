package com.zawmoehtike.data.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val tableId: Long = 0L,
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val genreIds: List<String>,
    val isFavorite: Boolean = false,

    var isNowPlaying: Boolean = false,
    var isUpComing: Boolean = false,
    var isPopular: Boolean = false
)
