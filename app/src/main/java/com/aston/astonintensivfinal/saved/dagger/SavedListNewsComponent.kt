package com.aston.astonintensivfinal.saved.dagger

import com.aston.astonintensivfinal.saved.presentation.ui.SavedListFragment
import dagger.Component

@Component(modules = [SavedListFactoryModule::class, SavedListModule::class])
interface SavedListNewsComponent {
    fun inject(savedListFragment: SavedListFragment)
}