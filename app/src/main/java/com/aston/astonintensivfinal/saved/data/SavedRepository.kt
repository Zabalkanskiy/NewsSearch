package com.aston.astonintensivfinal.saved.data

import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsModelEntity
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain

interface SavedRepository {
    fun loadNewsFromDatabase(): List<NewsModelEntity>

    fun mapNewsFromDatabaseInModel(listDBNews: List<NewsModelEntity>): List<SavedNewsModelDomain>
}