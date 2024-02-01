package com.search.news.headlines.data

import com.search.news.core.data.databaseNews.NewsModel.NewsModelEntity
import com.search.news.headlines.domain.model.FullNewsDomain.FullNewsDomainModel

interface HeadlinesFullNewsRepository {
    fun saveFullNews(news: NewsModelEntity)

    fun deleteFullNews(news: NewsModelEntity)

    fun findNewsInDataBase(title: String, urlToImage: String): Boolean

    fun mapToNewsModelEntity(fullNewsDomainModel: FullNewsDomainModel): NewsModelEntity
}