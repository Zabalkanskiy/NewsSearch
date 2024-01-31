package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepositoryImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.FindSourcesFromDataBaseUseCaseImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.GetSourcesUseCase
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.LoadSourcesFromDataBaseUseCaseImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCaseImpl
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