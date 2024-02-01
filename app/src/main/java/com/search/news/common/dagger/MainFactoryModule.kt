package com.search.news.common.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.search.news.common.presentation.viewModel.MainViewModel
import com.search.news.sources.dagger.ViewModelKey
import com.search.news.sources.presentation.viewmodel.SourceListViewModelFactory
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