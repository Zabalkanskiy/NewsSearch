package com.aston.astonintensivfinal.sources.domain.sourceListUseCase

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import javax.inject.Inject

class SaveInDataBaseSourcesUseCaseImpl @Inject constructor(private val sourcesRepository: SourcesRepository) : SaveInDataBaseSourcesUseCase {
   override suspend fun saveSourcesInDb(listSourceDomain: List<SourceNewsDomain>) {
        sourcesRepository.mapToListSourceNewsModelEntity(listSourceDomain).let {
            sourcesRepository.saveSourcesInDatabase(it)
        }

    }
}