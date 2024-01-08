package com.aston.astonintensivfinal.data.databaseNews.NewsModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
class NewsModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val urlToImage:String ,
    val title: String,
    val content: String,
    val publishedAt: String,
    val sourceName: String
    )
