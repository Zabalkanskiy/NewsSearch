package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.dagger.AppComponent
import com.aston.astonintensivfinal.headlines.dagger.HeadlinesModule
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesGeneralFragment
import com.aston.astonintensivfinal.sources.presentation.ui.OneSourceLIstFragment
import com.aston.astonintensivfinal.sources.presentation.ui.SourceListFragment
import dagger.Component

@SourcesScope
@Component(modules = [(SourcesModule::class), (SourceFactoryModule::class), (OneSourceListMOdule::class)],
    dependencies = [AppComponent::class])
interface SourcesComponent {

    fun inject(sourceListFragment: SourceListFragment)

    fun inject(oneSourceLIstFragment: OneSourceLIstFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SourcesComponent
    }
}