package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepositoryImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.GetSourcesUseCase
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
}