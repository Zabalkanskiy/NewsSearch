package com.aston.astonintensivfinal.headlines.dagger

import com.aston.astonintensivfinal.AstonIntensivApplication
import com.aston.astonintensivfinal.data.databaseNews.NewsModel.NewsDao
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepository
import com.aston.astonintensivfinal.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.aston.astonintensivfinal.headlines.data.HeadlinesRepository
import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.fullNewsUseCases.DeleteFullNewsUseCase
import com.aston.astonintensivfinal.headlines.domain.fullNewsUseCases.FindNewsInDatabaseUseCase
import com.aston.astonintensivfinal.headlines.domain.fullNewsUseCases.SaveFullNewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import moxy.MvpPresenter

@Module
abstract class HeadlinesFullNewsModule {

    @Binds
    abstract fun bindHeadlinesFullNewsRepository(repository: HeadlinesFullNewsRepositoryImpl): HeadlinesFullNewsRepository

    // @Binds
    // abstract fun bindHeadLinesFullNewsPresenter(headlinesFullNewsPresenter: HeadlinesFullNewsPresenter): MvpPresenter<HeadlinesFullNewsView>

    companion object {

        @Provides
        fun provideDeleteFullNewsUseCase(): DeleteFullNewsUseCase {
            return DeleteFullNewsUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = AstonIntensivApplication.getAstonApplicationContext.newsDao
                )
            )
        }

        @Provides
        fun provideFindNewsDatabaseUseCase(): FindNewsInDatabaseUseCase {
            return FindNewsInDatabaseUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = AstonIntensivApplication.getAstonApplicationContext.newsDao
                )
            )

        }

        @Provides
        fun provideSaveFullNewsUseCase(): SaveFullNewsUseCase {
            return SaveFullNewsUseCase(
                headlinesFullNewsRepository = HeadlinesFullNewsRepositoryImpl(
                    newsDao = AstonIntensivApplication.getAstonApplicationContext.newsDao
                )
            )
        }


    }

}