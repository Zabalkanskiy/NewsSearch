package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle

interface SaveNewsSourceInDBUseCase {
    suspend fun saveNewsInDB(listNews: List<OneSourceNewsArticle>,  language: String)
}