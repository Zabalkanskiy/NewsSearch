package com.aston.astonintensivfinal.saved.data

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(private val databaseNewsDao: NewsDao) :
    SavedRepository {
    override suspend fun loadNewsFromDatabase(language: String): List<NewsModelEntity> {
        return databaseNewsDao.getAllLanguageSavedNews(language = language)
    }

    override suspend fun loadRangeNewsFromDB(
        language: String,
        startDate: Date,
        endDate: Date
    ): List<NewsModelEntity> {
        return databaseNewsDao.getRangeLanguageSavedNews(language = language, startDate = startDate, endDate = endDate)
    }

    override suspend fun searchNewsInRepository(
        language: String,
        query: String
    ): List<NewsModelEntity> {
        return databaseNewsDao.searchSavedNewsByQuery(language = language, query = query)
    }

    override fun mapNewsFromDatabaseInModel(listDBNews: List<NewsModelEntity>): List<SavedNewsModelDomain> {

        fun dateToTimestamp(date: Date): String {
            val format = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US)
            return date.let { format.format(it) }
        }
        return listDBNews.map {
            SavedNewsModelDomain(
                urlToImage = it.urlToImage,
                titleNews = it.title,
                content = it.content,
                publishedAt = dateToTimestamp(it.publishedAt),
                source = it.sourceName,
                description = it.description,
                url = it.url,
                idSource = it.sourceId
            )
        }
    }
}