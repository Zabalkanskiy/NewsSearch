package com.search.news.common.dagger

import com.search.news.common.presentation.ui.MainActivity
import dagger.Component

@Component(modules = [MainFactoryModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}