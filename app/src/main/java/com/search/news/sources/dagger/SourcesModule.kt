package com.search.news.sources.dagger

import com.search.news.sources.data.repository.SourcesRepositoryImpl
import com.search.news.sources.domain.sourceListUseCase.FindSourcesFromDataBaseUseCaseImpl
import com.search.news.sources.domain.sourceListUseCase.GetSourcesUseCase
import com.search.news.sources.domain.sourceListUseCase.LoadSourcesFromDataBaseUseCaseImpl
import com.search.news.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class SourcesModule {

    @SourcesScope
    @Provides
    fun getSourcesUseCase(): GetSourcesUseCase = GetSourcesUseCase(SourcesRepositoryImpl())
    @SourcesScope
    @Provides
    fun getSourcesRepositoryImpl(): SourcesRepositoryImpl = SourcesRepositoryImpl()

    @SourcesScope
    @Provides
    fun getLoadSourcesFromDataBaseUseCaseImpl(): LoadSourcesFromDataBaseUseCaseImpl = LoadSourcesFromDataBaseUseCaseImpl(SourcesRepositoryImpl())

    @SourcesScope
    @Provides
    fun getSaveInDataBaseSourcesUseCaseImpl(): SaveInDataBaseSourcesUseCaseImpl = SaveInDataBaseSourcesUseCaseImpl(SourcesRepositoryImpl())

    @SourcesScope
    @Provides
    fun provideFindSourcesFromDataBaseUseCaseImpl(): FindSourcesFromDataBaseUseCaseImpl = FindSourcesFromDataBaseUseCaseImpl(SourcesRepositoryImpl())


}