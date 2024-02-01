package com.search.news.sources.domain.sourceListUseCase

import com.search.news.sources.data.repository.SourcesRepository
import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain
import javax.inject.Inject

class SaveInDataBaseSourcesUseCaseImpl @Inject constructor(private val sourcesRepository: SourcesRepository) : SaveInDataBaseSourcesUseCase {
   override suspend fun saveSourcesInDb(listSourceDomain: List<SourceNewsDomain>) {
        sourcesRepository.mapToListSourceNewsModelEntity(listSourceDomain).let {
            sourcesRepository.saveSourcesInDatabase(it)
        }

    }
}