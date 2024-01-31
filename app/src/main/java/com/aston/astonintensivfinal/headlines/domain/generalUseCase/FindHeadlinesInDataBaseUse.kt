package com.aston.astonintensivfinal.headlines.domain.generalUseCase

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import javax.inject.Inject

class FindHeadlinesInDataBaseUse @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl) {

   suspend fun findHeadlinesInDataBase(searchQuery: String): List<HeadlinesNewsModelData>{

        return repositoryImpl.findNewsInDataBase(searchString = searchQuery).let {
            repositoryImpl.mapFromDBNewsInDomain(it)
        }
    }
}