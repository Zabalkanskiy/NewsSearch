package com.aston.astonintensivfinal.headlines.domain.generalUseCase

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import java.util.Date
import javax.inject.Inject


class LoadHeadlinesFromDataBaseUseCase @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl) {

   suspend fun loadFromDataBase(category: String, language: String): List<HeadlinesNewsModelData>{

       return repositoryImpl.loadNewsFromDataBase(category = category, language = language).let {
           repositoryImpl.mapFromDBNewsInDomain(it)
       }
    }
}