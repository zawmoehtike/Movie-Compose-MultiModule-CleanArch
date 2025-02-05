package com.zawmoehtike.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zawmoehtike.data.cache.entities.GenreEntity

/**
 * Created by P.T.H.W on 26/04/2024.
 */

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(list: List<GenreEntity>)

    @Query("SELECT * FROM genre WHERE id=:id")
    fun getGenreById(id: Int): GenreEntity

    @Query("SELECT * FROM genre")
    fun getAllGenres(): List<GenreEntity>

    @Query("DELETE FROM genre")
    suspend fun clearGenres()
}