package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.headlines.domain.model.FullNewsDomain.FullNewsDomainModel
import javax.inject.Inject

class HeadlinesFullNewsRepositoryImpl @Inject constructor(private val newsDao: NewsDao) :
    HeadlinesFullNewsRepository {
    override fun saveFullNews(news: NewsModelEntity) {
        newsDao.insertNews(news)
    }

    override fun deleteFullNews(news: NewsModelEntity) {
        newsDao.deleteNewsByUrlImageTitleAndPublishedAt(urlToImage = news.urlToImage, title = news.title, publishedAt = news.publishedAt)
    }

    override fun findNewsInDataBase(title: String, urlToImage: String): Boolean {
        return newsDao.countNewsByTitleAndUrlImage(title, urlToImage)
    }


    override fun mapToNewsModelEntity(fullNewsDomainModel: FullNewsDomainModel): NewsModelEntity{
        return NewsModelEntity(urlToImage = fullNewsDomainModel.urlToImage, title = fullNewsDomainModel.title, content = fullNewsDomainModel.content, publishedAt = fullNewsDomainModel.publishedAt, sourceName = fullNewsDomainModel.sourceName)
    }
}