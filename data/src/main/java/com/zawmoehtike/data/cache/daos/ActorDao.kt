package com.zawmoehtike.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zawmoehtike.data.cache.entities.ActorEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by P.T.H.W on 03/04/2024.
 */

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(list: List<ActorEntity>)

    @Query("SELECT * FROM actor")
    fun getAllActors(): Flow<List<ActorEntity>>

    @Query("DELETE FROM actor")
    suspend fun clearActors()
}