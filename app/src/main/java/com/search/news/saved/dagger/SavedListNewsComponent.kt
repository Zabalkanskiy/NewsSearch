package com.search.news.saved.dagger

import com.search.news.saved.presentation.ui.SavedListFragment
import dagger.Component

@Component(modules = [SavedListFactoryModule::class, SavedListModule::class])
interface SavedListNewsComponent {
    fun inject(savedListFragment: SavedListFragment)
}