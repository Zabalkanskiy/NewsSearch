package com.search.news.saved.dagger

import com.search.news.SearchApplication
import com.search.news.core.data.databaseNews.NewsModel.NewsDao
import com.search.news.saved.data.SavedRepository
import com.search.news.saved.data.SavedRepositoryImpl
import com.search.news.saved.domain.savedListUseCase.LoadDataRangeSavedListUseCase
import com.search.news.saved.domain.savedListUseCase.LoadDataRangeSavedListUseCaseImpl
import com.search.news.saved.domain.savedListUseCase.LoadDataSavedListUseCase
import com.search.news.saved.domain.savedListUseCase.LoadDataSavedListUseCaseImpl
import com.search.news.saved.domain.savedListUseCase.SearchSavedNewsUseCase
import com.search.news.saved.domain.savedListUseCase.SearchSavedNewsUseCaseImpl
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
        return  SearchApplication.getAstonApplicationContext.newsDao

      }

  }
}