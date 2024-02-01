package com.search.news.sources.dagger

import com.search.news.sources.data.repository.SourcesRepository
import com.search.news.sources.data.repository.SourcesRepositoryImpl
import com.search.news.sources.domain.sourceListUseCase.LoadSourcesFromDataBaseUseCaseImpl
import com.search.news.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCase
import com.search.news.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface SourcesBindsModule {
    @SourcesScope
    @Binds
    fun bindLoadSourcesFromDataBaseUseCase(useCase: LoadSourcesFromDataBaseUseCaseImpl): LoadSourcesFromDataBaseUseCaseImpl
    @SourcesScope
    @Binds
    fun bindSaveInDataBaseSourcesUseCase(useCase: SaveInDataBaseSourcesUseCaseImpl) : SaveInDataBaseSourcesUseCase
    @SourcesScope
    @Binds
    fun bindSourcesRepository(repositoryImpl: SourcesRepositoryImpl) : SourcesRepository
}