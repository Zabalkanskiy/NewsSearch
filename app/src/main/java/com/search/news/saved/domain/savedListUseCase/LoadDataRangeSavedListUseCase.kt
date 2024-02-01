package com.search.news.saved.domain.savedListUseCase

import com.search.news.saved.domain.model.SavedNewsModelDomain
import java.util.Date

interface LoadDataRangeSavedListUseCase {
    suspend fun getRangeNewsFromDB(language: String, startDate: Date, endDate: Date ): List<SavedNewsModelDomain>
}