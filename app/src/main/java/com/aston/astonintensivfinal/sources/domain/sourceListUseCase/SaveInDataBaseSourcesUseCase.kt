package com.aston.astonintensivfinal.sources.domain.sourceListUseCase

import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain

interface SaveInDataBaseSourcesUseCase {
    suspend fun saveSourcesInDb(listSourceDomain: List<SourceNewsDomain>)
}