package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles

interface LoadNewsSourceFromDBUseCase {
    suspend fun loadNewsFromDB(sourceId:String, language:String): OneSourceNewsListArticles
}