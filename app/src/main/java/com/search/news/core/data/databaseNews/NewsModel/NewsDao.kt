package com.search.news.core.data.databaseNews.NewsModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): List<NewsModelEntity>

    @Query("SELECT * FROM news  WHERE saved = 1 ORDER BY publishedAt DESC")
    fun getAllSavedNews(): List<NewsModelEntity>
    @Query("SELECT * FROM news  WHERE saved = 1 AND language = :language ORDER BY publishedAt DESC")
    fun getAllLanguageSavedNews(language: String): List<NewsModelEntity>

    @Query("SELECT * FROM news  WHERE saved = 1 AND language = :language AND publishedAt BETWEEN :startDate AND :endDate ORDER BY publishedAt DESC")
    fun getRangeLanguageSavedNews(language: String, startDate: Date, endDate: Date): List<NewsModelEntity>

    @Query("SELECT * FROM news WHERE category = :category ORDER BY publishedAt DESC")
    fun getNewsByCategorySortedByDate(category: String): List<NewsModelEntity>

    @Query("SELECT * FROM news WHERE category = :category AND language = :language ORDER BY publishedAt DESC")
    fun getNewsByCategoryAndLanguageSortedByDate(category: String, language: String):  List<NewsModelEntity>

    @Query("SELECT * FROM news WHERE category = :category AND language = :language AND publishedAt BETWEEN :startDate AND :endDate ORDER BY publishedAt DESC")
    fun getNewsByCategoryAndLanguageAndDateSortedByDate(category: String, language: String, startDate: Date, endDate: Date):  List<NewsModelEntity>

    @Query("SELECT * FROM news WHERE sourceId LIKE :sourceId AND language = :language AND publishedAt BETWEEN :startDate AND :endDate ORDER BY publishedAt DESC")
    fun getNewsBySourceAndLanguageAndDateSortedByDate(sourceId: String, language: String, startDate: Date, endDate: Date):  List<NewsModelEntity>

    @Query("SELECT * FROM news_every")
    fun getEverythikAllNews(): List<NewsEverythikModelEntity>

    @Insert
    fun insertNews(news: NewsModelEntity)

    @Update
    fun updateNews(news: NewsModelEntity)

    @Delete
    fun deleteNews(news: NewsModelEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveListNews(newsList: List<NewsModelEntity>)
   @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveEverythingNews(newsList: List<NewsModelEntity>)



    @Query("SELECT * FROM news WHERE sourceId LIKE :sourceId AND language = :language ORDER BY publishedAt DESC")
    fun findNewsBySourceName(sourceId: String, language: String): List<NewsModelEntity>

    @Query("SELECT * FROM news_every WHERE urlToImage LIKE '%' || :searchString || '%' " +
            "OR title LIKE '%' || :searchString || '%' " +
            "OR content LIKE '%' || :searchString || '%' " +
            "OR sourceName LIKE '%' || :searchString || '%' " +
            "OR sourceId LIKE '%' || :searchString || '%'" +
            "OR description LIKE '%' || :searchString || '%' " +
            "OR url LIKE '%' || :searchString || '%' " +
            "ORDER BY publishedAt DESC")
    fun searchEverythinkNews(searchString: String): List<NewsEverythikModelEntity>


    @Query("SELECT * FROM news WHERE urlToImage LIKE '%' || :searchString || '%' " +
            "OR title LIKE '%' || :searchString || '%' " +
            "OR content LIKE '%' || :searchString || '%' " +
            "OR sourceName LIKE '%' || :searchString || '%' " +
            "OR sourceId LIKE '%' || :searchString || '%'" +
            "OR description LIKE '%' || :searchString || '%' " +
            "OR url LIKE '%' || :searchString || '%' " +
            "ORDER BY publishedAt DESC")
    fun searchNewsByPartialString(searchString: String): List<NewsModelEntity>

    @Query("SELECT * FROM news WHERE urlToImage LIKE '%' || :query || '%' " +
            "OR title LIKE '%' || :query || '%' " +
            "OR content LIKE '%' || :query || '%' " +
            "OR sourceName LIKE '%' || :query || '%' " +
            "OR description LIKE '%' || :query || '%' " +
            "OR url LIKE '%' || :query || '%' " +
            " AND sourceId LIKE :sourceId AND language = :language " +
            "ORDER BY publishedAt DESC")
    fun searchNewsByQueryAndSources(   sourceId: String,
                                       language: String,
                                       query: String) : List<NewsModelEntity>


    @Query("SELECT * FROM news WHERE urlToImage LIKE '%' || :query || '%' " +
            "OR title LIKE '%' || :query || '%' " +
            "OR content LIKE '%' || :query || '%' " +
            "OR sourceName LIKE '%' || :query || '%' " +
            "OR description LIKE '%' || :query || '%' " +
            "OR url LIKE '%' || :query || '%' " +
            " AND saved = 1 AND language = :language " +
            "ORDER BY publishedAt DESC")
    fun searchSavedNewsByQuery(
        language: String,
        query: String): List<NewsModelEntity>



    @Query("DELETE FROM news WHERE urlToImage = :urlToImage AND title = :title AND publishedAt = :publishedAt")
    fun deleteNewsByUrlImageTitleAndPublishedAt(urlToImage: String, title: String, publishedAt: Date)

    @Query("UPDATE news SET saved = :saved WHERE urlToImage = :urlToImage AND title = :title AND publishedAt = :publishedAt")
    fun updateNewsSavedStatus(urlToImage: String, title: String, publishedAt: Date, saved: Boolean)
    @Query("UPDATE news_every SET saved = :saved WHERE urlToImage = :urlToImage AND title = :title AND publishedAt = :publishedAt")
    fun updateEverythingNewsSavedStatus(urlToImage: String, title: String, publishedAt: Date, saved: Int)

    @Query("SELECT COUNT(*) > 0 FROM news WHERE title = :title AND urlToImage = :urlToImage AND saved = 1")
    fun countNewsByTitleAndUrlImage(title: String, urlToImage: String): Boolean

    @Query("DELETE FROM news WHERE publishedAt < :twoWeeksAgo AND saved = 1")
    fun deleteNewsOlderThanTwoWeeks(twoWeeksAgo: Date)

    @Query("SELECT * FROM source_news WHERE language = :language ORDER BY name ASC")
    fun getAllSourceNews(language: String): List<SourceNewsModelEntity>

    @Insert
    fun insertSourceNews(sourceNews: SourceNewsModelEntity)

    @Update
    fun updateSourceNews(sourceNews: SourceNewsModelEntity)

    @Delete
    fun deleteSourceNews(sourceNews: SourceNewsModelEntity)

    @Query("SELECT * FROM source_news WHERE id = :sourceId")
    fun getSourceNewsById(sourceId: String): SourceNewsModelEntity?

    @Query("DELETE FROM source_news WHERE id = :sourceId")
    fun deleteSourceNewsById(sourceId: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllSourceNews(sourceNewsList: List<SourceNewsModelEntity>)

    @Query("SELECT * FROM source_news WHERE id LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' OR language LIKE '%' || :query || '%' OR country LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchSimilarSourceNews(query: String): List<SourceNewsModelEntity>
}

