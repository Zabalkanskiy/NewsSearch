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

    @Query("DELETE FROM news WHERE urlToImage = :urlToImage AND title = :title AND publishedAt = :publishedAt")
    fun deleteNewsByUrlImageTitleAndPublishedAt(urlToImage: String, title: String, publishedAt: String)

    @Query("SELECT COUNT(*) > 0 FROM news WHERE title = :title AND urlToImage = :urlToImage")
    fun countNewsByTitleAndUrlImage(title: String, urlToImage: String): Boolean
}