package com.aston.astonintensivfinal.headlines.domain.generalUseCase

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.model.HeadlinesNewsDomain.HeadlinesNewsModelData
import javax.inject.Inject

class SaveHeadlinesDataInDataBaseUseCase @Inject constructor(private val repositoryImpl: HeadlinesRepositoryImpl){

 suspend   fun saveDataInDataBase(listDomainNews: List<HeadlinesNewsModelData>, category:String, language: String){
        repositoryImpl.mapFromDomainNewsInDB(newsList = listDomainNews).map { it.copy(category = category, language = language) }.let {
            repositoryImpl.saveNewsInDataBase(newsList = it)
        }
    }
}