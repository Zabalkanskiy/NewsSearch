package com.search.news.sources.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.search.news.sources.presentation.viewmodel.OneSourceListViewModel
import com.search.news.sources.presentation.viewmodel.SourceListViewModel
import com.search.news.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SourceFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourceListViewModel::class)
    fun bindViewModel(sourceListViewModel: SourceListViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(OneSourceListViewModel::class)
    fun bindOneSourceListViewModel(oneSourceListViewModel: OneSourceListViewModel): ViewModel
}

