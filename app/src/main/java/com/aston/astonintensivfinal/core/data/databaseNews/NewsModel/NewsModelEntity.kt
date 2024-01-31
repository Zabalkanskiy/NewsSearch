package com.aston.astonintensivfinal.core.data.databaseNews.NewsModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "news")
data class NewsModelEntity(
   // @PrimaryKey(autoGenerate = true)
   // val id: Long = 0,

    @PrimaryKey
    val urlToImage:String ,
    val title: String,
    val content: String,
    @TypeConverters(DateTypeConverters::class)
    val publishedAt: Date,
    val sourceName: String,
    val sourceId: String,
    val description: String,
    val url: String,
    @TypeConverters(BooleanTypeConverter::class)
    val saved: Boolean = false,
    val category: String = "everything",
    val language: String = "en"
    )
