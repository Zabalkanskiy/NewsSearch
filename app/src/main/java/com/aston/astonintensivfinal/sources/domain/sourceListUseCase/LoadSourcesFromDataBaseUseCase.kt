package com.aston.astonintensivfinal.sources.domain.sourceListUseCase

import com.aston.astonintensivfinal.sources.domain.model.sourceListModel.SourceNewsDomain

interface LoadSourcesFromDataBaseUseCase {
    suspend fun loadSourcesFromDB(language: String): List<SourceNewsDomain>
}