package com.search.news.sources.dagger

import com.search.news.headlines.data.HeadlinesFullNewsRepository
import com.search.news.headlines.data.HeadlinesFullNewsRepositoryImpl
import com.search.news.sources.data.repository.OneSourceRepositoryImpl
import com.search.news.sources.data.repository.OneSourcesRepository
import com.search.news.sources.domain.oneSourcesUesCase.FindNewsFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.FindNewsFromDBUseCaseImpl
import com.search.news.sources.domain.oneSourcesUesCase.GetNewsSourceUseCase
import com.search.news.sources.domain.oneSourcesUesCase.GetNewsSourceUseCaseImpl
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsByDateFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsByDateFromDBUseCaseImpl
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsSourceFromDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.LoadNewsSourceFromDBUseCaseImpl
import com.search.news.sources.domain.oneSourcesUesCase.SaveNewsSourceInDBUseCase
import com.search.news.sources.domain.oneSourcesUesCase.SaveNewsSourceInDBUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class OneSourceListMOdule {
    @SourcesScope
    @Binds
    abstract fun bindGetNewSourcesUseCase(useCase: GetNewsSourceUseCaseImpl): GetNewsSourceUseCase


    @SourcesScope
    @Binds
    abstract fun bindsLoadNewsSourceFromDBUseCaseImpl(useCase: LoadNewsSourceFromDBUseCaseImpl): LoadNewsSourceFromDBUseCase

    @SourcesScope
    @Binds
    abstract fun bindsLoadNewsByDateFromDBUseCaseImpl(useCase: LoadNewsByDateFromDBUseCaseImpl): LoadNewsByDateFromDBUseCase

    @SourcesScope
    @Binds
    abstract fun bindsFindNewsFromDBUseCase(useCase: FindNewsFromDBUseCaseImpl) : FindNewsFromDBUseCase

    @SourcesScope
    @Binds
    abstract fun bindsSaveNewsSourceInDBUseCaseImpl(useCase: SaveNewsSourceInDBUseCaseImpl): SaveNewsSourceInDBUseCase

    @SourcesScope
    @Binds
    abstract fun bindGetOneSourceRepository(oneSourceRepositoryImpl: OneSourceRepositoryImpl): OneSourcesRepository

    @SourcesScope
    @Binds
    abstract fun bindHeadlinesFullNewsRepository(repository: HeadlinesFullNewsRepositoryImpl): HeadlinesFullNewsRepository


}