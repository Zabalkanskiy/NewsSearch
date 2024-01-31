package com.aston.astonintensivfinal.saved.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.core.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepository
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.aston.astonintensivfinal.saved.data.SavedRepository
import com.aston.astonintensivfinal.saved.data.SavedRepositoryImpl
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataRangeSavedListUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataRangeSavedListUseCaseImpl
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCaseImpl
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.SearchSavedNewsUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.SearchSavedNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SavedListModule {

    @Binds
  abstract   fun bindSavedRepository(repository: SavedRepositoryImpl): SavedRepository

     @Binds
  abstract   fun bindLoadDataSavedListUseCase(loadDataSavedListUseCaseImpl: LoadDataSavedListUseCaseImpl): LoadDataSavedListUseCase

  @Binds
  abstract fun bindLoadDataRangeSaveListUseCase(loadDataRangeSavedListUseCaseImpl: LoadDataRangeSavedListUseCaseImpl): LoadDataRangeSavedListUseCase

  @Binds
  abstract fun bindSearchSavedNewsUseCase(searchSavedNewsUseCaseImpl: SearchSavedNewsUseCaseImpl): SearchSavedNewsUseCase

  companion object {
      @Provides
      fun provideNewsDao(): NewsDao {
        return  AstonIntensivApplication.getAstonApplicationContext.newsDao

      }

  }
}