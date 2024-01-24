package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.data.headlinesmodel.ApiResponse
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import retrofit2.http.Query

interface OneSourcesRepository {
 suspend fun fetchOneSourceNews(apiKey: String, page: Int, pageSize: Int = 20, source: String, search: String): ApiResponse

 suspend fun saveNewsInDataBase(listNewsModelEntity : List<NewsModelEntity>)

 suspend fun loadNewsFromDataBase(sourceId : String): List<NewsModelEntity>
 suspend fun mapOneSourceNewsInDomain(apiResponse: ApiResponse): OneSourceNewsDomain

 suspend fun mapFromOneSourceToDomain(listNewsModelEntity: List<NewsModelEntity>): List<OneSourceNewsArticle>

 suspend fun mapInNewsModelFromDomain(listOneSourceNewsArticle: List<OneSourceNewsArticle>): List<NewsModelEntity>
}