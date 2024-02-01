package com.search.news.saved.domain.savedListUseCase

import com.search.news.saved.data.SavedRepository
import com.search.news.saved.domain.model.SavedNewsModelDomain
import javax.inject.Inject

class LoadDataSavedListUseCaseImpl @Inject constructor(private val savedRepository: SavedRepository) :
    LoadDataSavedListUseCase {
    override suspend fun loadNewsFromDataBase(language: String): List<SavedNewsModelDomain> {
        return savedRepository.loadNewsFromDatabase(language = language).let {
            savedRepository.mapNewsFromDatabaseInModel(it)
        }
    }
}