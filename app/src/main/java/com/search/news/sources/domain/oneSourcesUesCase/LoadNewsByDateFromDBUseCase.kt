package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import java.util.Date

interface LoadNewsByDateFromDBUseCase {
    suspend fun loadNewsByDateFromDB(sourceId:String, language:String, startDate: Date, endDate: Date): OneSourceNewsListArticles
}