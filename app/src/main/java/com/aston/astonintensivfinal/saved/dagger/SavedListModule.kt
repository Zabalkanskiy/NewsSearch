package com.aston.astonintensivfinal.saved.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepository
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.aston.astonintensivfinal.saved.data.SavedRepository
import com.aston.astonintensivfinal.saved.data.SavedRepositoryImpl
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCase
import com.aston.astonintensivfinal.saved.domain.savedListUseCase.LoadDataSavedListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SavedListModule {

    @Binds
  abstract   fun bindSavedRepository(repository: SavedRepositoryImpl): SavedRepository

     @Binds
  abstract   fun bindLoadDataSavedListUseCase(loadDataSavedListUseCaseImpl: LoadDataSavedListUseCaseImpl): LoadDataSavedListUseCase

  companion object {
      @Provides
      fun provideNewsDao(): NewsDao{
        return  AstonIntensivApplication.getAstonApplicationContext.newsDao

      }

  }
}