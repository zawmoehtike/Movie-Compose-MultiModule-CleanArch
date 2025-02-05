package com.zawmoehtike.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zawmoehtike.data.cache.daos.GenreDao
import com.zawmoehtike.data.cache.entities.GenreEntity
import com.zawmoehtike.data.cache.typeconverters.IntegerListConverter
import com.zawmoehtike.data.cache.typeconverters.StringListConverter

/**
 * Created by P.T.H.W on 30/03/2023.
 */

@Database(
    entities = [GenreEntity::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(
    IntegerListConverter::class,
    StringListConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
}