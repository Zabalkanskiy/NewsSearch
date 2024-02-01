package com.search.news.core.data.databaseNews.NewsModel

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
