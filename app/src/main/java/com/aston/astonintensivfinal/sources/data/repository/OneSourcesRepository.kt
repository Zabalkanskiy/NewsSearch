package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.core.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import retrofit2.http.Query
import java.util.Date

interface OneSourcesRepository {
    suspend fun fetchOneSourceNews(
        apiKey: String,
        page: Int,
        pageSize: Int = 20,
        source: String,
        search: String,
        language: String,
        sortBy: String,
        fromDate: String,
        toDate: String
    ): ApiResponse

    suspend fun saveNewsInDataBase(listNewsModelEntity: List<NewsModelEntity>)

    suspend fun loadNewsFromDataBase(sourceId: String,  language: String): List<NewsModelEntity>

    suspend fun loadNewsByDateFromDataBase(sourceId: String,
                                           language: String,
                                           startDate: Date,
                                           endDate: Date) : List<NewsModelEntity>

    suspend fun findNewsByQueryFromDataBase( sourceId: String,
                                             language: String,
                                             query: String): List<NewsModelEntity>
    suspend fun mapOneSourceNewsInDomain(apiResponse: ApiResponse): OneSourceNewsDomain

    suspend fun mapFromOneSourceToDomain(listNewsModelEntity: List<NewsModelEntity>): List<OneSourceNewsArticle>

    suspend fun mapInNewsModelFromDomain(listOneSourceNewsArticle: List<OneSourceNewsArticle>): List<NewsModelEntity>
}