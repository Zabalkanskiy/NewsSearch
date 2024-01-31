package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.common.mviState.Language
import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain

interface SearchSavedNewsUseCase {
  suspend  fun searchSavedNews(language: String, query:String): List<SavedNewsModelDomain>
}