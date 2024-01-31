package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles

interface FindNewsFromDBUseCase {
    suspend fun findNewsFromDataBase(sourceId:String,language: String, query:String): OneSourceNewsListArticles
}