package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import javax.inject.Inject

class LoadNewsSourceFromDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) : LoadNewsSourceFromDBUseCase {
    override suspend fun loadNewsFromDB(
        sourceId: String,
        language: String
    ): OneSourceNewsListArticles {
      val list: List<OneSourceNewsArticle> =  oneSourcesRepository.loadNewsFromDataBase(sourceId = sourceId, language = language).let {
            oneSourcesRepository.mapFromOneSourceToDomain(it)
        }
        return OneSourceNewsListArticles(totalResults = list.size, articles = list)
    }
}