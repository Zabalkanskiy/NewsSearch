package com.search.news.saved.domain.savedListUseCase

import com.search.news.saved.domain.model.SavedNewsModelDomain

interface LoadDataSavedListUseCase {

  suspend  fun loadNewsFromDataBase(language: String): List<SavedNewsModelDomain>
}