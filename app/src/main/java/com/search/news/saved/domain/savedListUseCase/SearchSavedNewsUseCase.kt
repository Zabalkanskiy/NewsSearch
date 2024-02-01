package com.search.news.saved.domain.savedListUseCase

import com.search.news.saved.domain.model.SavedNewsModelDomain

interface SearchSavedNewsUseCase {
  suspend  fun searchSavedNews(language: String, query:String): List<SavedNewsModelDomain>
}