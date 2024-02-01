package com.search.news.sources.domain.sourceListUseCase

import com.search.news.sources.domain.model.sourceListModel.SourceNewsDomain

interface SaveInDataBaseSourcesUseCase {
    suspend fun saveSourcesInDb(listSourceDomain: List<SourceNewsDomain>)
}