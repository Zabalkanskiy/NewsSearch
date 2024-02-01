package com.search.news.headlines.dagger

import com.search.news.headlines.presentation.ui.HeadlinesFullNewsFragment
import com.search.news.headlines.presentation.ui.HeadlinesGeneralFragment
import dagger.Component

@Component(modules = [(HeadlinesModule::class), (HeadlinesFullNewsModule::class), (FullNewsFactoryModule::class)])
interface HeadlinesComponent {
    fun inject(headlinesGeneralFragment: HeadlinesGeneralFragment)
    fun inject(headlinesFullNewsFragment: HeadlinesFullNewsFragment)
}