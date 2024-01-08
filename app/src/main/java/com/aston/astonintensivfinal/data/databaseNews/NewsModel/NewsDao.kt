package com.aston.astonintensivfinal.data.databaseNews.NewsModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): List<NewsModelEntity>

    @Insert
    fun insertNews(news: NewsModelEntity)

    @Update
    fun updateNews(news: NewsModelEntity)

    @Delete
    fun deleteNews(news: NewsModelEntity)

    @Query("SELECT EXISTS(SELECT COUNT(*) FROM news WHERE title = :title AND urlToImage = :urlToImage)")
    fun countNewsByTitleAndUrlImage(title: String, urlToImage: String): Boolean
}