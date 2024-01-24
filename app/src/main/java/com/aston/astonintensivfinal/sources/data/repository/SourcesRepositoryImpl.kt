package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.SourceNewsModelEntity
import com.aston.astonintensivfinal.data.sourcemodel.NewsSourceErrorResponse
import com.aston.astonintensivfinal.data.sourcemodel.NewsSourceResponse
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.NewsSourceErrorResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.NewsSourceResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceResponseDomain
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor() : SourcesRepository {
    override suspend fun fetchSourcesNews(
        apiKey: String,
        page: Int,
        pageSize: Int
    ): SourceResponse {

        return AstonIntensivApplication.getAstonApplicationContext.headlinesApi.getFilterSource(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize
        )


    }

    override suspend fun mapSourcesNews(
        apiKey: String,
        page: Int,
        pageSize: Int
    ): SourceResponseDomain {
        val sourceResponse = fetchSourcesNews(apiKey = apiKey, page = page, pageSize = pageSize)
        when (sourceResponse) {
            is NewsSourceResponse -> {
                var listSourceNewsDomain: MutableList<SourceNewsDomain> = mutableListOf()

                sourceResponse.sources?.map {
                    listSourceNewsDomain.add(
                        SourceNewsDomain(
                            id = it?.id,
                            name = it?.name,
                            category = it?.category,
                            language = it?.language,
                            country = it?.country
                        )
                    )
                }
                return NewsSourceResponseDomain(sources = listSourceNewsDomain)
            }

            is NewsSourceErrorResponse -> {

                return NewsSourceErrorResponseDomain(
                    status = sourceResponse.status,
                    code = sourceResponse.code,
                    message = sourceResponse.message
                )
            }

        }
    }

    override suspend fun saveSourcesInDatabase(listSourceNewsModelEntity: List<SourceNewsModelEntity>) {
        AstonIntensivApplication.getAstonApplicationContext.newsDao.insertAllSourceNews(
            sourceNewsList = listSourceNewsModelEntity
        )
    }

    override suspend fun loadFromSourcesInDataBase(): List<SourceNewsModelEntity> {
        return AstonIntensivApplication.getAstonApplicationContext.newsDao.getAllSourceNews()
    }

    override suspend fun findSourcesFromDataBase(query: String): List<SourceNewsModelEntity> {
       return AstonIntensivApplication.getAstonApplicationContext.newsDao.searchSimilarSourceNews(query = query)
    }


    override suspend fun mapToListSourceNewsDomain(listSourceNewsModelEntity: List<SourceNewsModelEntity>): List<SourceNewsDomain> {
        return listSourceNewsModelEntity.map { SourceNewsDomain(id = it.id, name = it.name, category = it.category, language = it.language, country = it.country) }
    }

    override suspend fun mapToListSourceNewsModelEntity(listSourceNewsDomain: List<SourceNewsDomain>): List<SourceNewsModelEntity> {
        return listSourceNewsDomain.map { SourceNewsModelEntity(id = it.id ?: "", language = it.language ?: "", country = it.country ?: "", category = it.category ?: "", name =  it.name ?: "") }
    }
}