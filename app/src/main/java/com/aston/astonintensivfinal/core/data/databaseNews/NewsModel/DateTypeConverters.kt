package com.aston.astonintensivfinal.core.data.databaseNews.NewsModel

import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import java.text.ParseException
import java.util.Date
import java.util.Locale

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
