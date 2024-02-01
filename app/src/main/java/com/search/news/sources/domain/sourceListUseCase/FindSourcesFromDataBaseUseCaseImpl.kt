package com.search.news.sources.domain.sourceListUseCase

import com.search.news.sources.data.repository.SourcesRepository
import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain
import javax.inject.Inject

class FindSourcesFromDataBaseUseCaseImpl @Inject constructor(private val sourcesRepository: SourcesRepository) {
    suspend fun findSourcesFromDataBase(query:String): List<SourceNewsDomain>{
        return sourcesRepository.findSourcesFromDataBase(query).let {
            sourcesRepository.mapToListSourceNewsDomain(it)
        }
    }
}