package com.aston.astonintensivfinal.core.data.databaseNews.NewsModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_news")
class SourceNewsModelEntity(
  //  @PrimaryKey(autoGenerate = true)
   // val index: Long = 0,
    @PrimaryKey
    val id: String,


    val name: String,


    val category: String,


    val language: String,


    val country: String
)
