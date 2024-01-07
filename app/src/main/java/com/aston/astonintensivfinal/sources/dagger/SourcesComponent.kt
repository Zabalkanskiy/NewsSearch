package com.aston.astonintensivfinal.sources.dagger

import com.aston.astonintensivfinal.headlines.dagger.HeadlinesModule
import com.aston.astonintensivfinal.headlines.presentation.ui.HeadlinesGeneralFragment
import com.aston.astonintensivfinal.sources.presentation.ui.SourceListFragment
import dagger.Component

@Component(modules = [(SourcesModule::class), (SourceFactoryModule::class)])
interface SourcesComponent {

    fun inject(sourceListFragment: SourceListFragment)
}