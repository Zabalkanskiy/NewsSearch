package com.aston.astonintensivfinal.common.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.astonintensivfinal.common.presentation.viewModel.FilterViewModel
import com.aston.astonintensivfinal.sources.dagger.ViewModelKey
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FilterFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    fun bindViewModel(filterViewModel: FilterViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory
}