package com.search.news.headlines.dagger

import com.search.news.SearchApplication
import com.search.news.headlines.data.HeadlinesFullNewsRepository
import com.search.news.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.search.news.headlines.domain.fullNewsUseCases.DeleteFullNewsUseCase
import com.search.news.headlines.domain.fullNewsUseCases.FindNewsInDatabaseUseCase
import com.search.news.headlines.domain.fullNewsUseCases.SaveFullNewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HeadlinesFullNewsModule {

    @Binds
    abstract fun bindHeadlinesFullNewsRepository(repository: HeadlinesFullNewsRepositoryImpl): HeadlinesFullNewsRepository

    companion object {

        @Provides
        fun provideDeleteFullNewsUseCase(): DeleteFullNewsUseCase {
            return DeleteFullNewsUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = SearchApplication.getAstonApplicationContext.newsDao
                )
            )
        }

        @Provides
        fun provideFindNewsDatabaseUseCase(): FindNewsInDatabaseUseCase {
            return FindNewsInDatabaseUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = SearchApplication.getAstonApplicationContext.newsDao
                )
            )

        }

        @Provides
        fun provideSaveFullNewsUseCase(): SaveFullNewsUseCase {
            return SaveFullNewsUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = SearchApplication.getAstonApplicationContext.newsDao
                )
            )
        }


    }

}