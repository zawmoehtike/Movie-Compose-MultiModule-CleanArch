package com.zawmoehtike.data.cache.typeconverters

import androidx.room.TypeConverter

class IntegerListConverter {
    @TypeConverter
    fun fromIntegerList(integerList: List<Int>): String {
        return integerList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toIntegerList(data: String): List<Int> {
        return data.split(",").map { it.toInt() }
    }
}