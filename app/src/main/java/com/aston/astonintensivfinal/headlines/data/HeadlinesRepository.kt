package com.aston.astonintensivfinal.headlines.data

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.core.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import io.reactivex.rxjava3.core.Single
import java.util.Date

interface HeadlinesRepository {
    fun fetchHeadlineNews(
        apiKey: String,
        page: Int,
        pageSize: Int = 20,
        category: String = "general",
        language: String = "en",
        country: String = "us",
        query: String
    ): Single<ApiResponse>

    fun fetchHeadlinesNewsFromEverything(apiKey: String,
                                         page: Int,
                                         pageSize: Int = 20,
                                         dayFrom: String,
                                         dayTo: String,
                                         sortBy: String,
                                         query: String,
                                         language: String
                                         ): Single<ApiResponse>

    suspend fun findNewsInDataBase(searchString: String): List<NewsModelEntity>

    suspend fun loadNewsFromDataBase(category: String, language: String): List<NewsModelEntity>

    suspend fun loadNewsByDateFromDataBase(category: String, language: String, startDate: Date, endDate: Date): List<NewsModelEntity>


    suspend fun saveNewsInDataBase(newsList: List<NewsModelEntity>)
    fun mapHeadlinesNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        category: String,
        language: String,
        country: String,
        query: String
    ): Single<HeadlineNewsResponce>

    fun mapFromDBNewsInDomain(newsList: List<NewsModelEntity>): List<HeadlinesNewsModelData>

    fun mapFromDomainNewsInDB(newsList: List<HeadlinesNewsModelData>): List<NewsModelEntity>

    fun mapListHeadlinesNews(apiResponce: Single<ApiResponse>): Single<HeadlineNewsResponce>
}