package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.saved.data.SavedRepository
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import javax.inject.Inject

class SearchSavedNewsUseCaseImpl @Inject constructor(private val savedRepository: SavedRepository) : SearchSavedNewsUseCase {

    override suspend fun searchSavedNews(language: String, query: String): List<SavedNewsModelDomain> {
        return savedRepository.searchNewsInRepository(language = language, query = query).let {
            savedRepository.mapNewsFromDatabaseInModel(it)
        }
    }
}