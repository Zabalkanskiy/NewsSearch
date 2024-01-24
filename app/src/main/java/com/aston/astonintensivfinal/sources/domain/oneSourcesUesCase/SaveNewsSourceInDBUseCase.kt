package com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase

import com.aston.astonintensivfinal.sources.domain.model.OneSourceNewsModel.OneSourceNewsArticle

interface SaveNewsSourceInDBUseCase {
    suspend fun saveNewsInDB(listNews: List<OneSourceNewsArticle>)
}