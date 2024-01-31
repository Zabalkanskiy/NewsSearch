package com.aston.astonintensivfinal.core.data.databaseNews.NewsModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_news")
class SourceNewsModelEntity(

    @PrimaryKey
    val id: String,


    val name: String,


    val category: String,


    val language: String,


    val country: String
)
