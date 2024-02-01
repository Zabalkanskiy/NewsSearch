package com.search.news.core.data.databaseNews.NewsModel

import androidx.room.TypeConverter

class BooleanTypeConverter {
    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value != 0
    }
}