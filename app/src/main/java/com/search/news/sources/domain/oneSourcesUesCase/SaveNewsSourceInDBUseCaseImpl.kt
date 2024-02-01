package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.data.repository.OneSourcesRepository
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import javax.inject.Inject

class SaveNewsSourceInDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) : SaveNewsSourceInDBUseCase{
    override suspend fun saveNewsInDB(listNews: List<OneSourceNewsArticle>, language: String) {
        oneSourcesRepository.mapInNewsModelFromDomain(listNews).map { it.copy(language = language) }.let {
            oneSourcesRepository.saveNewsInDataBase(it)
        }

    }

}