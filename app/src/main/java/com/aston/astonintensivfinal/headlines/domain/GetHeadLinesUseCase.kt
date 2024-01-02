package com.aston.astonintensivfinal.headlines.domain

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.model.HeadlineNewsResponce
import io.reactivex.rxjava3.core.Single

class GetHeadLinesUseCase constructor(val repositoryImpl: HeadlinesRepositoryImpl) {

    fun getHeadlines(   apiKey: String,
                        page: Int,
                        pageSize: Int,
                        category: String,
                        language: String,
                        country: String): Single<HeadlineNewsResponce>{
       return repositoryImpl.mapHeadlinesNews(   apiKey,
           page,
           pageSize,
           category,
           language,
           country)
    }
}