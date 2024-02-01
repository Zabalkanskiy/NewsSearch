package com.search.news.headlines.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.search.news.headlines.presentation.presenter.FullNewsViewModel
import com.search.news.sources.dagger.ViewModelKey
import com.search.news.sources.presentation.viewmodel.SourceListViewModelFactory
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