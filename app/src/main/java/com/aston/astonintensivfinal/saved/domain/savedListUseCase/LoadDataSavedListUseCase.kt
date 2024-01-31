package com.aston.astonintensivfinal.saved.domain.savedListUseCase

import com.aston.astonintensivfinal.saved.domain.model.SavedNewsModelDomain

interface LoadDataSavedListUseCase {

  suspend  fun loadNewsFromDataBase(language: String): List<SavedNewsModelDomain>
}