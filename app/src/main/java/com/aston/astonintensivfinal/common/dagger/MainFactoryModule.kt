package com.aston.astonintensivfinal.common.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.astonintensivfinal.common.presentation.viewModel.MainViewModel
import com.aston.astonintensivfinal.sources.dagger.ViewModelKey
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainFactoryModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory
}