package com.search.news.sources.domain.sourceListUseCase

import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain

interface LoadSourcesFromDataBaseUseCase {
    suspend fun loadSourcesFromDB(language: String): List<SourceNewsDomain>
}