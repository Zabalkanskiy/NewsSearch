package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles

interface LoadNewsSourceFromDBUseCase {
    suspend fun loadNewsFromDB(sourceId:String): OneSourceNewsListArticles
}