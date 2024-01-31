package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.SourceNewsModelEntity
import com.aston.astonintensivfinal.core.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceResponseDomain

interface SourcesRepository {
 suspend  fun fetchSourcesNews(apiKey: String, page: Int, pageSize: Int, language: String ): SourceResponse

 suspend  fun mapSourcesNews(apiKey: String, page: Int, pageSize: Int, language:String): SourceResponseDomain

 suspend fun saveSourcesInDatabase(listSourceNewsModelEntity: List<SourceNewsModelEntity>)

 suspend fun loadFromSourcesInDataBase(language: String): List<SourceNewsModelEntity>

 suspend fun findSourcesFromDataBase(query: String) : List<SourceNewsModelEntity>

 suspend fun mapToListSourceNewsDomain(listSourceNewsModelEntity: List<SourceNewsModelEntity>): List<SourceNewsDomain>

 suspend fun mapToListSourceNewsModelEntity(listSourceNewsDomain: List<SourceNewsDomain>): List<SourceNewsModelEntity>
}