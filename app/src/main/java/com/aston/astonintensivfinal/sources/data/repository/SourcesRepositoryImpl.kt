package com.aston.astonintensivfinal.sources.data.repository

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.sourcemodel.NewsSourceErrorResponse
import com.aston.astonintensivfinal.data.sourcemodel.NewsSourceResponse
import com.aston.astonintensivfinal.data.sourcemodel.SourceResponse
import com.aston.astonintensivfinal.sources.domain.model.NewsSourceErrorResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.NewsSourceResponseDomain
import com.aston.astonintensivfinal.sources.domain.model.SourceNewsDomain
import com.aston.astonintensivfinal.sources.domain.model.SourceResponseDomain
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(): SourcesRepository {
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
        when(sourceResponse){
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
}