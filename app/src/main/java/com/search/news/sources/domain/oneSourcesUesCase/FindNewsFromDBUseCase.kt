package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles

interface FindNewsFromDBUseCase {
    suspend fun findNewsFromDataBase(sourceId:String,language: String, query:String): OneSourceNewsListArticles
}