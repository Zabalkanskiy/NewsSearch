package com.aston.astonintensivfinal.sources.domain.sourceListUseCase

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepository
import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain
import javax.inject.Inject

class LoadSourcesFromDataBaseUseCaseImpl @Inject constructor(private val sourcesRepository: SourcesRepository) : LoadSourcesFromDataBaseUseCase {
    override suspend fun loadSourcesFromDB(language: String): List<SourceNewsDomain> {
     return  sourcesRepository.loadFromSourcesInDataBase(language = language).let{
           sourcesRepository.mapToListSourceNewsDomain(it)
       }
    }
}