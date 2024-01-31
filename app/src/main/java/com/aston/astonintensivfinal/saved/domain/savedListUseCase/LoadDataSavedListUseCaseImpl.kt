package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.saved.data.SavedRepository
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import javax.inject.Inject

class LoadDataSavedListUseCaseImpl @Inject constructor(private val savedRepository: SavedRepository) :
    LoadDataSavedListUseCase {
    override suspend fun loadNewsFromDataBase(language: String): List<SavedNewsModelDomain> {
        return savedRepository.loadNewsFromDatabase(language = language).let {
            savedRepository.mapNewsFromDatabaseInModel(it)
        }
    }
}