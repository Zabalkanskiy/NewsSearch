package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import javax.inject.Inject

class SaveNewsSourceInDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) : SaveNewsSourceInDBUseCase{
    override suspend fun saveNewsInDB(listNews: List<OneSourceNewsArticle>, language: String) {
        oneSourcesRepository.mapInNewsModelFromDomain(listNews).map { it.copy(language = language) }.let {
            oneSourcesRepository.saveNewsInDataBase(it)
        }

    }

}