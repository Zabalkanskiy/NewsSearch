package com.search.news.headlines.domain.generalUseCase

import com.search.news.headlines.data.HeadlinesRepositoryImpl
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import javax.inject.Inject


class LoadHeadlinesFromDataBaseUseCase @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl) {

   suspend fun loadFromDataBase(category: String, language: String): List<HeadlinesNewsModelData>{

       return repositoryImpl.loadNewsFromDataBase(category = category, language = language).let {
           repositoryImpl.mapFromDBNewsInDomain(it)
       }
    }
}