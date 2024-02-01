package com.search.news.sources.domain.oneSourcesUesCase

import com.search.news.sources.data.repository.OneSourcesRepository
import com.search.news.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import javax.inject.Inject

class GetNewsSourceUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) :
    GetNewsSourceUseCase {
    override suspend fun getOneSourceNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        source: String,
        search: String,
        language: String,
        sortBy: String,
        fromDate: String,
        toDate: String
    ): OneSourceNewsDomain {
        return oneSourcesRepository.fetchOneSourceNews(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            source = source,
            search = search,
            language = language,
            sortBy = sortBy,
            fromDate = fromDate,
            toDate = toDate
        ).let {
            oneSourcesRepository.mapOneSourceNewsInDomain(it)
        }
    }
}