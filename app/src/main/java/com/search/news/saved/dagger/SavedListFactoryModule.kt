package com.search.news.saved.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.search.news.saved.presentation.repository.SavedListViewModel
import com.search.news.sources.dagger.ViewModelKey
import com.search.news.sources.presentation.viewmodel.SourceListViewModelFactory
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