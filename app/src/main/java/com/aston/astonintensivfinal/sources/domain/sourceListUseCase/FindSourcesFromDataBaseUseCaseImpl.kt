package com.aston.astonintensivfinal.sources.domain.sourceListUseCase

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import javax.inject.Inject

class FindSourcesFromDataBaseUseCaseImpl @Inject constructor(private val sourcesRepository: SourcesRepository) {
    suspend fun findSourcesFromDataBase(query:String): List<SourceNewsDomain>{
        return sourcesRepository.findSourcesFromDataBase(query).let {
            sourcesRepository.mapToListSourceNewsDomain(it)
        }
    }
}