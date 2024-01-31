package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import java.util.Date
import javax.inject.Inject

class LoadNewsByDateFromDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) : LoadNewsByDateFromDBUseCase {
    override suspend fun loadNewsByDateFromDB(
        sourceId: String,
        language: String,
        startDate: Date,
        endDate: Date
    ): OneSourceNewsListArticles {

      val list: List<OneSourceNewsArticle> =  oneSourcesRepository.loadNewsByDateFromDataBase(
            sourceId = sourceId,
            language = language,
            startDate = startDate,
            endDate = endDate).let {
            oneSourcesRepository.mapFromOneSourceToDomain(it)
        }

        return OneSourceNewsListArticles(totalResults = list.size, articles = list)
    }
}