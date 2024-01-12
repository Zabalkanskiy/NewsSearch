package com.aston.astonintensivfinal.saved.data

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(private val databaseNewsDao: NewsDao) :
    SavedRepository {
    override fun loadNewsFromDatabase(): List<NewsModelEntity> {
      return  databaseNewsDao.getAllNews()
    }

    override fun mapNewsFromDatabaseInModel(listDBNews: List<NewsModelEntity>): List<SavedNewsModelDomain>{

       return listDBNews.map { SavedNewsModelDomain(urlToImage = it.urlToImage, titleNews = it.title, content = it.content, publishedAt = it.publishedAt, source = it.sourceName) }
    }
}