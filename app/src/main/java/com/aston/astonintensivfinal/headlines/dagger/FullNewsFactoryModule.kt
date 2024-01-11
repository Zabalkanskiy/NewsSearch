package com.aston.astonintensivfinal.headlines.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.astonintensivfinal.headlines.presentation.presenter.FullNewsViewModel
import com.aston.astonintensivfinal.sources.dagger.ViewModelKey
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FullNewsFactoryModule {
    @Binds
    @IntoMap
    @ViewModelKey(FullNewsViewModel::class)
    fun bindViewModel(fullNewsViewModel: FullNewsViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory
}