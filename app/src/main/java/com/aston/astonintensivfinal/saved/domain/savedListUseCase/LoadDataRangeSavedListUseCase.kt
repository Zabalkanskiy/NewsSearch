package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import java.util.Date

interface LoadDataRangeSavedListUseCase {
    suspend fun getRangeNewsFromDB(language: String, startDate: Date, endDate: Date ): List<SavedNewsModelDomain>
}