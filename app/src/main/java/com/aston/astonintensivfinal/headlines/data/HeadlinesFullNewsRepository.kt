package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.headlines.domain.model.FullNewsDomain.FullNewsDomainModel

interface HeadlinesFullNewsRepository {
    fun saveFullNews(news: NewsModelEntity)

    fun deleteFullNews(news: NewsModelEntity)

    fun findNewsInDataBase(title: String, urlToImage: String): Boolean

    fun mapToNewsModelEntity(fullNewsDomainModel: FullNewsDomainModel): NewsModelEntity
}