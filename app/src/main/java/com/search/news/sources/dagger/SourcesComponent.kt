package com.search.news.sources.dagger

import com.search.news.dagger.AppComponent
import com.search.news.sources.presentation.ui.OneSourceLIstFragment
import com.search.news.sources.presentation.ui.SourceListFragment
import dagger.Component

@SourcesScope
@Component(modules = [(SourcesModule::class), (SourceFactoryModule::class), (OneSourceListMOdule::class),/* (SourcesBindsModule::class) */],
    dependencies = [AppComponent::class])
interface SourcesComponent {

    fun inject(sourceListFragment: SourceListFragment)

    fun inject(oneSourceLIstFragment: OneSourceLIstFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SourcesComponent
    }
}