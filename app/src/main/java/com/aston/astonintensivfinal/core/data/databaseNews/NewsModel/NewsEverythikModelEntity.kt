package com.aston.astonintensivfinal.core.data.databaseNews.NewsModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date
@Entity(tableName = "news_every")
class NewsEverythikModelEntity(
    @PrimaryKey
    val urlToImage:String,
    val title: String,
    val content: String,
    @TypeConverters(DateTypeConverters::class)
    val publishedAt: Date,
    val sourceName: String,
    val sourceId: String,
    val description: String,
    val url: String,
    val saved: Boolean = false,
)

