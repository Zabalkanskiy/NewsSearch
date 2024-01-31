package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.sources.data.repository.SourcesRepository
import com.aston.astonintensivfinal.sources.data.repository.SourcesRepositoryImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.LoadSourcesFromDataBaseUseCaseImpl
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCase
import com.aston.astonintensivfinal.sources.domain.sourceListUseCase.SaveInDataBaseSourcesUseCaseImpl
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