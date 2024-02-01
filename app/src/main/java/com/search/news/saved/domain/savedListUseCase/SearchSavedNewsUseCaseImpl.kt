package com.search.news.saved.domain.savedListUseCase

import com.search.news.saved.data.SavedRepository
import com.search.news.saved.domain.model.SavedNewsModelDomain
import javax.inject.Inject

class SearchSavedNewsUseCaseImpl @Inject constructor(private val savedRepository: SavedRepository) : SearchSavedNewsUseCase {

    override suspend fun searchSavedNews(language: String, query: String): List<SavedNewsModelDomain> {
        return savedRepository.searchNewsInRepository(language = language, query = query).let {
            savedRepository.mapNewsFromDatabaseInModel(it)
        }
    }
}