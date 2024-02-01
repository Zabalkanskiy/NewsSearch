package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.data.repository.OneSourcesRepository
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsListArticles
import javax.inject.Inject

class FindNewsFromDBUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) :
    FindNewsFromDBUseCase {
    override suspend fun findNewsFromDataBase(
        sourceId: String,
        language: String,
        query: String
    ): OneSourceNewsListArticles {
        val list: List<OneSourceNewsArticle> =     oneSourcesRepository.findNewsByQueryFromDataBase(
            sourceId = sourceId,
            language = language,
            query = query
        ).let {
            oneSourcesRepository.mapFromOneSourceToDomain(it)
        }

        return OneSourceNewsListArticles(totalResults = list.size, articles = list)
    }
}