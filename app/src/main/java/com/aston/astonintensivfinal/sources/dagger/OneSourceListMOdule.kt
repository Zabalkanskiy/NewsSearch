package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepository
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.fullNewsUseCases.DeleteFullNewsUseCase
import com.aston.astonintensivfinal.sources.data.repository.OneSourceRepositoryImpl
import com.aston.astonintensivfinal.sources.data.repository.OneSourcesRepository
import com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase.GetNewsSourceUseCase
import com.aston.astonintensivfinal.sources.domain.oneSourcesUesCase.GetNewsSourceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class OneSourceListMOdule {
    @SourcesScope
    @Binds
    abstract fun bindGetNewSourcesUseCase(useCase: GetNewsSourceUseCaseImpl): GetNewsSourceUseCase

    @SourcesScope
    @Binds
    abstract fun bindGetOneSourceRepository(oneSourceRepositoryImpl: OneSourceRepositoryImpl): OneSourcesRepository

    @SourcesScope
    @Binds
    abstract fun bindHeadlinesFullNewsRepository(repository: HeadlinesFullNewsRepositoryImpl): HeadlinesFullNewsRepository

    /*companion object {

        @Provides
        fun provideNewsDao(): NewsDao {
            return AstonIntensivApplication.getAstonApplicationContext.newsDao
        }
    }

     */
}