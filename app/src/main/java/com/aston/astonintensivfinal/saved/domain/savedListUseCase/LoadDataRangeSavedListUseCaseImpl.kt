package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.saved.data.SavedRepository
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import java.util.Date
import javax.inject.Inject

class LoadDataRangeSavedListUseCaseImpl @Inject constructor(private val savedRepository: SavedRepository): LoadDataRangeSavedListUseCase {
    override suspend fun getRangeNewsFromDB(
        language: String,
        startDate: Date,
        endDate: Date
    ): List<SavedNewsModelDomain> {
       return savedRepository.loadRangeNewsFromDB(language = language, startDate = startDate, endDate = endDate).let {
            savedRepository.mapNewsFromDatabaseInModel(it)

        }
    }
}