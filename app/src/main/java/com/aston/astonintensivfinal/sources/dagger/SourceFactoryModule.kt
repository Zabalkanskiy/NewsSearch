package com.aston.astonintensivfinal.sources.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModel
import com.aston.astonintensivfinal.sources.presentation.viewmodel.SourceListViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface SourceFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(SourceListViewModel::class)
    fun bindViewModel(sourceListViewModel: SourceListViewModel): ViewModel

    @Binds
    fun bindViewModelsFactory(viewModelsFactory: SourceListViewModelFactory): ViewModelProvider.Factory
}

