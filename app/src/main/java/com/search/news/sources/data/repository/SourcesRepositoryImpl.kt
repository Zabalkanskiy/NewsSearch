package com.search.news.sources.data.repository

import com.search.news.SearchApplication
import com.search.news.core.data.databaseNews.NewsModel.SourceNewsModelEntity
import com.search.news.core.data.sourcemodel.NewsSourceErrorResponse
import com.search.news.core.data.sourcemodel.NewsSourceResponse
import com.search.news.core.data.sourcemodel.SourceResponse
import com.search.news.sources.domain.model.sourceListModel.NewsSourceErrorResponseDomain
import com.search.news.sources.domain.model.sourceListModel.NewsSourceResponseDomain
import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain
import com.search.news.sources.domain.model.sourceListModel.SourceResponseDomain
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor() : SourcesRepository {
    override suspend fun fetchSourcesNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        language:String
    ): SourceResponse {

        return SearchApplication.getAstonApplicationContext.headlinesApi.getFilterSource(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            language = language
        )


    }

    override suspend fun mapSourcesNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        language: String
    ): SourceResponseDomain {
        val sourceResponse = fetchSourcesNews(apiKey = apiKey, page = page, pageSize = pageSize, language = language)
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
        SearchApplication.getAstonApplicationContext.newsDao.insertAllSourceNews(
            sourceNewsList = listSourceNewsModelEntity
        )
    }

    override suspend fun loadFromSourcesInDataBase(language: String): List<SourceNewsModelEntity> {
        return SearchApplication.getAstonApplicationContext.newsDao.getAllSourceNews(language = language)
    }

    override suspend fun findSourcesFromDataBase(query: String): List<SourceNewsModelEntity> {
       return SearchApplication.getAstonApplicationContext.newsDao.searchSimilarSourceNews(query = query)
    }


    override suspend fun mapToListSourceNewsDomain(listSourceNewsModelEntity: List<SourceNewsModelEntity>): List<SourceNewsDomain> {
        return listSourceNewsModelEntity.map { SourceNewsDomain(id = it.id, name = it.name, category = it.category, language = it.language, country = it.country) }
    }

    override suspend fun mapToListSourceNewsModelEntity(listSourceNewsDomain: List<SourceNewsDomain>): List<SourceNewsModelEntity> {
        return listSourceNewsDomain.map { SourceNewsModelEntity(id = it.id ?: "", language = it.language ?: "", country = it.country ?: "", category = it.category ?: "", name =  it.name ?: "") }
    }
}