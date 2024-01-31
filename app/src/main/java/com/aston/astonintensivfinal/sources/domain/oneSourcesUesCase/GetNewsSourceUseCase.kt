package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain

interface GetNewsSourceUseCase {
    suspend fun getOneSourceNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        source: String,
        search: String,
        language: String,
        sortBy: String,
        fromDate: String,
        toDate: String
    ): OneSourceNewsDomain
}