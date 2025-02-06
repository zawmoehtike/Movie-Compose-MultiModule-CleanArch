package com.zawmoehtike.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zawmoehtike.data.cache.daos.*
import com.zawmoehtike.data.cache.entities.*
import com.zawmoehtike.data.cache.typeconverters.IntegerListConverter
import com.zawmoehtike.data.cache.typeconverters.StringListConverter

/**
 * Created by P.T.H.W on 30/03/2023.
 */

@Database(
    entities = [MovieEntity::class,
        ActorEntity::class,
        GenreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    IntegerListConverter::class,
    StringListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun actorDao(): ActorDao
    abstract fun genreDao(): GenreDao
}