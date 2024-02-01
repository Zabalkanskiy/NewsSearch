package com.search.news.headlines.domain.generalUseCase

import com.search.news.headlines.data.HeadlinesRepositoryImpl
import com.search.news.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import javax.inject.Inject

class FindHeadlinesInDataBaseUse @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl) {

   suspend fun findHeadlinesInDataBase(searchQuery: String): List<HeadlinesNewsModelData>{

        return repositoryImpl.findNewsInDataBase(searchString = searchQuery).let {
            repositoryImpl.mapFromDBNewsInDomain(it)
        }
    }
}