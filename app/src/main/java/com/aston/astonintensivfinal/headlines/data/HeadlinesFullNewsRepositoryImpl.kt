package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.headlines.domain.model.FullNewsDomain.FullNewsDomainModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class HeadlinesFullNewsRepositoryImpl @Inject constructor(private val newsDao: NewsDao) :
    HeadlinesFullNewsRepository {
    override fun saveFullNews(news: NewsModelEntity) {
        newsDao.updateNewsSavedStatus(
            urlToImage = news.urlToImage,
            title = news.title,
            publishedAt = news.publishedAt,
            saved = true
        )
    }

    override fun deleteFullNews(news: NewsModelEntity) {
        newsDao.updateNewsSavedStatus(
            urlToImage = news.urlToImage,
            title = news.title,
            publishedAt = news.publishedAt,
            saved = false
        )
    }

    override fun findNewsInDataBase(title: String, urlToImage: String): Boolean {
        return newsDao.countNewsByTitleAndUrlImage(title, urlToImage)
    }


    override fun mapToNewsModelEntity(fullNewsDomainModel: FullNewsDomainModel): NewsModelEntity {
        fun fromTimestamp(value: String?): Date? {
            return value?.let { SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.US).parse(it) }
        }
        return NewsModelEntity(
            urlToImage = fullNewsDomainModel.urlToImage,
            title = fullNewsDomainModel.title,
            content = fullNewsDomainModel.content,
            publishedAt = fromTimestamp(fullNewsDomainModel.publishedAt) as Date,
            sourceName = fullNewsDomainModel.sourceName,
            url = fullNewsDomainModel.url,
            description = fullNewsDomainModel.description,
            sourceId = fullNewsDomainModel.idSource

        )
    }
}