package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsDomain
import javax.inject.Inject

class GetNewsSourceUseCaseImpl @Inject constructor(private val oneSourcesRepository: OneSourcesRepository) :
    GetNewsSourceUseCase {
    override suspend fun getOneSourceNews(
        apiKey: String,
        page: Int,
        pageSize: Int,
        source: String,
        search: String
    ): OneSourceNewsDomain {
        return oneSourcesRepository.fetchOneSourceNews(
            apiKey = apiKey,
            page = page,
            pageSize = pageSize,
            source = source,
            search = search
        ).let {
            oneSourcesRepository.mapOneSourceNewsInDomain(it)
        }
    }
}