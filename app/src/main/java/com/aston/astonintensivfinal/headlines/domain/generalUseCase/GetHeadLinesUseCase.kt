package com.aston.astonintensivfinal.headlines.domain.generalUseCase

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlineNewsResponce
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetHeadLinesUseCase @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl) {

    /*    fun getHeadlines(   apiKey: String,
                            page: Int,
                            pageSize: Int,
                            category: String,
                            language: String,
                            country: String,
                            query: String): Single<HeadlineNewsResponce>{
           return repositoryImpl.mapHeadlinesNews(
               apiKey,
               page,
               pageSize,
               category,
               language,
               country,
               query = query)
        }*/

    fun getHeadlines(
        apiKey: String,
        page: Int,
        pageSize: Int,
        dayFrom: String,
        dayTo: String,
        sortBy: String,
        query: String,
        language: String
    ): Single<HeadlineNewsResponce> {
        return repositoryImpl.fetchHeadlinesNewsFromEverything(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            dayFrom = dayFrom,
            dayTo = dayTo,
            sortBy = sortBy,
            query = query,
            language = language
        ).let {
            repositoryImpl.mapListHeadlinesNews(it)
        }
    }
}