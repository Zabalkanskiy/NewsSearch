package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.headlines.data.HeadlinesRepositoryImpl
import com.aston.astonintensivfinal.headlines.domain.GetHeadLinesUseCase
import com.aston.astonintensivfinal.sources.data.repository.SourcesRepositoryImpl
import com.aston.astonintensivfinal.sources.domain.GetSourcesUseCase
import dagger.Module
import dagger.Provides

@Module
class SourcesModule {

    @Provides
    fun getSourcesUseCase(): GetSourcesUseCase = GetSourcesUseCase(SourcesRepositoryImpl())
    @Provides
    fun getSourcesRepositoryImpl(): SourcesRepositoryImpl = SourcesRepositoryImpl()
}