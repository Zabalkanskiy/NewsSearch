package com.aston.astonintensivfinal.saved.data

import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain
import java.util.Date

interface SavedRepository {
    suspend fun loadNewsFromDatabase(language: String): List<NewsModelEntity>

    suspend fun loadRangeNewsFromDB(
        language: String,
        startDate: Date,
        endDate: Date
    ): List<NewsModelEntity>

    suspend fun searchNewsInRepository(language: String, query:String): List<NewsModelEntity>


    fun mapNewsFromDatabaseInModel(listDBNews: List<NewsModelEntity>): List<SavedNewsModelDomain>
}