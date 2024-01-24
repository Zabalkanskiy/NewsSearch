package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.SourceNewsModelEntity
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceResponseDomain

interface SourcesRepository {
 suspend  fun fetchSourcesNews(apiKey: String, page: Int, pageSize: Int ): SourceResponse

 suspend  fun mapSourcesNews(apiKey: String, page: Int, pageSize: Int): SourceResponseDomain

 suspend fun saveSourcesInDatabase(listSourceNewsModelEntity: List<SourceNewsModelEntity>)

 suspend fun loadFromSourcesInDataBase(): List<SourceNewsModelEntity>

 suspend fun findSourcesFromDataBase(query: String) : List<SourceNewsModelEntity>

 suspend fun mapToListSourceNewsDomain(listSourceNewsModelEntity: List<SourceNewsModelEntity>): List<SourceNewsDomain>

 suspend fun mapToListSourceNewsModelEntity(listSourceNewsDomain: List<SourceNewsDomain>): List<SourceNewsModelEntity>
}