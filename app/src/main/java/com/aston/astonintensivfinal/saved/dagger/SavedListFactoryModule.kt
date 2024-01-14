package com.aston.astonintensivfinal.saved.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.astonintensivfinal.saved.presentation.repository.SavedListViewModel
import com.aston.astonintensivfinal.sources.dagger.ViewModelKey
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SavedListFactoryModule {
    @Binds
    @IntoMap
    @ViewModelKey(SavedListViewModel::class)
    fun bindViewModel(savedListViewModel: SavedListViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory

}