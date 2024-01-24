package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import javax.inject.Inject

class SaveNewsSourceInDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) : SaveNewsSourceInDBUseCase{
    override suspend fun saveNewsInDB(listNews: List<OneSourceNewsArticle>) {
        oneSourcesRepository.mapInNewsModelFromDomain(listNews).let {
            oneSourcesRepository.saveNewsInDataBase(it)
        }

    }

}