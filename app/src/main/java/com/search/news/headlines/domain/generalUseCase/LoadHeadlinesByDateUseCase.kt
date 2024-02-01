package com.search.news.headlines.domain.generalUseCase

import com.search.news.headlines.data.HeadlinesRepositoryImpl
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import java.util.Date
import javax.inject.Inject

class LoadHeadlinesByDateUseCase  @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl){

    suspend fun loadFromDataBaseByDate(category: String, language: String, startDate: Date, endDate: Date): List<HeadlinesNewsModelData>{
        return repositoryImpl.loadNewsByDateFromDataBase(category = category, language = language, startDate = startDate, endDate = endDate).let {
            repositoryImpl.mapFromDBNewsInDomain(it)
        }
    }

}